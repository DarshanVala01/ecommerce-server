package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.request.AddToCartRequest;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartService cartService;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart() throws UserException{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        // Fetch the user details from your user service or repository
        User user = this.userRepository.findByEmail(email);
        
		Cart cart = this.cartService.findUserCart(user.getId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PostMapping("/cart/add")
	public ResponseEntity<String> addCartItem(@RequestBody AddToCartRequest request) throws ProductException{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        // Fetch the user details from your user service or repository
        User user = this.userRepository.findByEmail(email);
        
        System.out.println("User Id in the Cart Controller :"+user.getId());
		
		String response = this.cartService.addCartItem(user.getId(), request);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
}
