package com.ank.mysite.dao;

import com.ank.mysite.entity.User;

public interface UserDao {

	User findByName(String username);
}
