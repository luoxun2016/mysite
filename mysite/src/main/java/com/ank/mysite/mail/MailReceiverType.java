package com.ank.mysite.mail;

import java.util.Properties;

public enum MailReceiverType {
	/** 网易POP3 */
	MAIL163_POP3 {
		@Override
		public Properties getProperties() {
			Properties props = getDefaultProperties(Protocol.POP3);
			props.setProperty("mail.pop3.host", "pop.163.com");
			return props;
		}
	},
	/** 腾讯邮箱POP3  */
	MAILQQ_POP3 {
		@Override
		public Properties getProperties() {
			Properties props = getDefaultProperties(Protocol.POP3);
			props.setProperty("mail.pop3.host", "pop.qq.com");
			return props;
		}
	};

	public static enum Protocol {
		POP3 {
			public String getName() {return "pop3";}
		},
		IMAP {
			public String getName() {return "imap";}
		};
		public abstract String getName();
	}

	protected Properties getDefaultProperties(Protocol pro) {
		Properties props = new Properties();
		String protocol = pro.getName();
		props.setProperty("mail.store.protocol", protocol);
		/**默认启3用SSL 端口为995*/
		props.setProperty(String.format("mail.%s.port", protocol), "995");
		/**启用SSL start*/
		props.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
		props.setProperty(String.format("mail.%s.ssl.enable", protocol), "true");
		/**启用SSL end*/
		props.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
		props.setProperty(String.format("mail.%s.starttls.enable", protocol), "true");
		props.setProperty(String.format("mail.%s.disabletop", protocol), "true");
		props.setProperty(String.format("mail.%s.useStartTLS", protocol), "true");
		return props;
	}
	
	public abstract Properties getProperties();
}