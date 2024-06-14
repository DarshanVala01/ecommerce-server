package com.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile() throws UserException {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();

        // Fetch the user details from your user service or repository
        User user = this.userRepository.findByEmail(email);
       return new ResponseEntity<User>(user,HttpStatus.OK);
    }
}

