package com.ank.mysite.service;

import java.util.Set;

import com.ank.mysite.entity.Mail;

public interface MailService {

	void addMail(Mail mail);
	
	Set<String> getAllRecvMailUid();
	
}
