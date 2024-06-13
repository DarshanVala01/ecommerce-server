package com.ecommerce.service;

import java.util.List;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(long userId,long id,CartItem cartItem) throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart , Product product,String size, long userId);
	
	public void removeCartItem(long userId , long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(long cartItemId) throws CartItemException;
	
	public List<CartItem> allCartItem();
}
