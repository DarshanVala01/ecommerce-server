package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.CartItemService;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartItemService cartItemService;
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) throws UserException, CartItemException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        User user = this.userRepository.findByEmail(email);
		this.cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse response = new ApiResponse();
		response.setMessage("Item Deleted Successfully From Cart");
		response.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
}
