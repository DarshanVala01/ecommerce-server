package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.request.AddToCartRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart() throws UserException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        User user = this.userRepository.findByEmail(email);
        
		Cart cart = this.cartService.findUserCart(user.getUserId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addCartItem(@RequestBody AddToCartRequest request) throws ProductException, UserException, CartItemException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        User user = this.userRepository.findByEmail(email);
        
		this.cartService.addCartItem(user.getUserId(), request);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Item added to cart");
		apiResponse.setStatus(true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
}
