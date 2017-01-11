package com.ank.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ank.mysite.dao.UserDao;
import com.ank.mysite.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User findByName(String username) {
		return userDao.findByName(username);
	}
}
