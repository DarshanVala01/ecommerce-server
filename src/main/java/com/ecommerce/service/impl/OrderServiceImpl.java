package com.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.OrderException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private CartRepository cartRepository;
//	@Autowired
//	private CartService cartItemService;
	@Autowired
	private ProductService productService;
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOrderById(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> usersOrderHistory(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order placedOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order confirmedOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order shippedOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliveredOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order canceledOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders() {  
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		
	}

}
