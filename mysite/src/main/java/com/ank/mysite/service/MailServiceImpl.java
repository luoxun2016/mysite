package com.ank.mysite.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ank.mysite.dao.MailDao;
import com.ank.mysite.entity.Mail;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private MailDao mailDao;

	public void addMail(Mail mail) {
		mailDao.addMail(mail);
	}
	
	public Set<String> getAllRecvMailUid() {
		Set<String> s = new HashSet<String>(mailDao.getAllMailUid());
		return s;
	}
}
