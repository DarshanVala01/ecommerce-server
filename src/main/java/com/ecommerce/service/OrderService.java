package com.ecommerce.service;

import java.util.List;

import com.ecommerce.exception.OrderException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

public interface OrderService {

	public Order createOrder(User user , Address shippingAddress);
	
	public Order findOrderById(long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(long userId);
	
	public Order placedOrder(long orderId) throws OrderException;
	
	public Order confirmedOrder(long orderId) throws OrderException;
	
	public Order shippedOrder(long orderId) throws OrderException;
	
	public Order deliveredOrder(long orderId) throws OrderException;
	
	public Order canceledOrder(long orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public void deleteOrder(long orderId) throws OrderException;
}
