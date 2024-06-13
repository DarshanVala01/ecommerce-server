package com.ecommerce.service;

import org.springframework.stereotype.Service;

import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;

@Service
public interface UserService {
	
	public User findUserById(long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findByEmail(String email);
}
