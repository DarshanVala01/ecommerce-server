package com.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.service.CartItemService;
import com.ecommerce.service.UserService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserService userService;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
		
		CartItem cretaedCartItem = this.cartItemRepository.save(cartItem);
		return cretaedCartItem;
	}

	@Override
	public CartItem updateCartItem(long userId, long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartItemById(id);
		User user = this.userService.findUserById(item.getUserId());
		
		if (user.getId() == userId) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
		}
		CartItem updatedItem = this.cartItemRepository.save(item);
		return updatedItem;
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, long userId) {
		CartItem cartItem = this.cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(long userId, long cartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(cartItemId);
		User user = this.userService.findUserById(cartItem.getUserId());
		
		User reqUser = this.userService.findUserById(userId);
		
		if (user.getId() == reqUser.getId()) {
			this.cartItemRepository.deleteById(cartItemId);
		}else {
			throw new UserException("You can't remove another users item");
		}
		
	}

	@Override
	public CartItem findCartItemById(long cartItemId) throws CartItemException {
		Optional<CartItem> cartItem = this.cartItemRepository.findById(cartItemId);
		if (cartItem.isPresent()) {
			return cartItem.get();
		}
		throw new CartItemException("CartItem not found with Id :"+cartItemId);
	}

	@Override
	public List<CartItem> allCartItem() {
		List<CartItem> allCartItems = this.cartItemRepository.findAll();
		return allCartItems;
	}

}
