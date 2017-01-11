package com.ank.mysite.dao;

import java.util.List;

import com.ank.mysite.entity.Mail;

public interface MailDao {

	void addMail(Mail mail);
	
	List<String> getAllMailUid();
}
