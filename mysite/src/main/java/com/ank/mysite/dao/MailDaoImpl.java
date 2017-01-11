package com.ank.mysite.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ank.mysite.entity.Mail;

@Repository
public class MailDaoImpl implements MailDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addMail(Mail mail) {
		Session session = sessionFactory.getCurrentSession();
		session.save(mail);
	}
	
	public List<String> getAllMailUid() {
		Session session = sessionFactory.getCurrentSession();
		Query<String> query = session.createQuery("select uid from Mail as mail", String.class);
		List<String> list = query.getResultList();
		return list;
	}
}
