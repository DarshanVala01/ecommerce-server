package com.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.request.AddToCartRequest;
import com.ecommerce.service.CartItemService;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		
		Cart createdCart = this.cartRepository.save(cart);
		return createdCart;
	}

	@Override
	public String addCartItem(long userId, AddToCartRequest request) throws ProductException {
		Cart cart = this.cartRepository.findCartByUserId(userId);
		Product product = this.productService.findProductById(request.getProductId());
		
		CartItem isPresent = this.cartItemService.isCartItemExist(cart, product, request.getSize(), userId);
		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setUserId(userId);
			
			int price = request.getQuantity() * product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(request.getSize());
			
			CartItem createdCartItem = this.cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}
		return "Item Added to Cart";
	}

	@Override
	public Cart findUserCart(long userId) {
		
		Cart cart = this.cartRepository.findCartByUserId(userId);
		System.out.println("Cart :"+cart);
		System.out.println("Cart Items :"+cart.getCartItems());
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(totalItem);
		cart.setDiscount(totalPrice - totalDiscountedPrice);
		
		Cart savedCart =  this.cartRepository.save(cart);
		return savedCart;
	}

}
