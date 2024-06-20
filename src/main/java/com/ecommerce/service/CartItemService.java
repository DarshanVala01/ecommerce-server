package com.ecommerce.service;

import java.util.List;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

public interface CartItemService {
	
	public CartItem updateCartItem(Long userId,Long cartItemId,CartItem cartItem) throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart , Product product,String size, Long userId);
	
	public void removeCartItem(Long userId , Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
	public List<CartItem> allCartItem();
}
