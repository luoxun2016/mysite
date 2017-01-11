package com.ank.mysite.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ank.mysite.entity.Mail;
import com.ank.mysite.entity.MaillAttach;
import com.ank.mysite.helper.FileHelper;
import com.ank.mysite.service.MailService;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

public class SimpleMailReceiver {
	private static final Logger LOG = Logger.getLogger(SimpleMailReceiver.class);
	
	public static String savePath = "d:/mail";
	
	@Autowired
	private MailService mailService;
	
	public void fetchInbox(MailReceiverType type, String username, String password){
		try {
			Store store = null;
			Folder folder = null;
			try{
				/**初始化会话Session*/
				Properties props = type.getProperties();
				Authenticator authenticator = AuthenticatorGenerator.getAuthenticator(username, password);
				Session session = Session.getDefaultInstance(props, authenticator);
				/**初始化Store*/
				store = session.getStore();
				store.connect();
				/**获取收件箱*/
				folder = store.getFolder("INBOX");
				/**以只读方式打开收件箱*/
				folder.open(Folder.READ_ONLY);
				/**获取所有邮件*/
				Message[] messages = folder.getMessages();
				
				/** 打印不同状态的邮件数量*/
				LOG.info("收件箱中共" + messages.length + "封邮件!");
				LOG.info("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
				LOG.info("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
				LOG.info("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");
				
				download(folder, messages);
			}finally{
				if(folder != null) folder.close(false);
				if(store != null) store.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void download(Folder folder, Message[] messages){
		try {
			Set<String> recvMailUidSet = mailService.getAllRecvMailUid();
			for(int i = 0 ; i < messages.length ; i++){
				Message message = messages[i];
				
				String uid = null;
				if(folder instanceof POP3Folder){
					uid = ((POP3Folder)folder).getUID(message);
				}else if(folder instanceof IMAPFolder){
					uid = String.valueOf(((IMAPFolder)folder).getUID(message));
				}
				
				if(recvMailUidSet.contains(uid)){
					LOG.info("邮件 " + i + "已经下载过！");
					continue;
				}
				
				InternetAddress from = (InternetAddress)message.getFrom()[0];
				InternetAddress to = (InternetAddress)message.getReplyTo()[0];
				
				MimeMessageParser parser = new MimeMessageParser((MimeMessage)message).parse();
				
				Mail mail = new Mail();
				mail.setUid(uid);
				mail.setFromAddr(from.getAddress());
				mail.setFromPersonal(from.getPersonal());
				mail.setSubject(message.getSubject());
				mail.setToAddr(to.getAddress());
				mail.setHasAttachments(parser.hasAttachments());
				mail.setHtmlContent(parser.getHtmlContent());
				mail.setPlainContent(parser.getPlainContent());
				
				if(parser.hasAttachments()){
					List<MaillAttach> attachments = new ArrayList<MaillAttach>();
					List<DataSource> dataSourceList = parser.getAttachmentList();
					
					for(DataSource ds : dataSourceList){
						String path = savePath + File.separator + to.getAddress() + File.separator + uid + File.separator + ds.getName();
						boolean ret = FileHelper.writeFile(ds.getInputStream(), path) ;
						if(ret){
							MaillAttach attach = new MaillAttach();
							attach.setContentType(ds.getContentType());
							attach.setName(ds.getName());
							attach.setMail(mail);
							attachments.add(attach);
						}
					}
					mail.setAttachments(attachments);
				}
				
				mailService.addMail(mail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
		SimpleMailReceiver mailReceiver = ctx.getBean(SimpleMailReceiver.class);
		mailReceiver.fetchInbox(MailReceiverType.MAIL163_POP3, "luoxunbj@163.com", "20402321lx");
	}
}
