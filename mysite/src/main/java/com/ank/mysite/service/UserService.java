package com.ank.mysite.service;

import com.ank.mysite.entity.User;

public interface UserService {

	User findByName(String username);
	
}
