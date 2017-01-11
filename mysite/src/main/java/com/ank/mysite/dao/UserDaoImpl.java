package com.ank.mysite.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ank.mysite.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public User findByName(String username) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from User as user where user.username=:username";
		Query<User> query = session.createQuery(hql, User.class);
		query.setParameter("username", username);
		List<User> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
