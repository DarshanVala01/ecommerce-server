package com.ecommerce.service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.request.AddToCartRequest;

public interface CartService {

	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddToCartRequest request) throws ProductException ;
	
	public Cart findUserCart(Long userId);
}
