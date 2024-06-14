package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.OrderException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Address address = this.addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		this.userRepository.save(user);
		
		Cart cart = this.cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem cartItem : cart.getCartItems()) {
			
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSize(cartItem.getSize());
			orderItem.setUserId(cartItem.getUserId());
			orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
			
			OrderItem createdOrderItem = this.orderItemRepository.save(orderItem);
			orderItems.add(createdOrderItem);
		}
		
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order saveOrder = this.orderRepository.save(createdOrder);
		
		for(OrderItem item : orderItems){
			item.setOrder(saveOrder);
			this.orderItemRepository.save(item);
		}
		return saveOrder;
	}

	@Override
	public Order findOrderById(long orderId) throws OrderException {
		Optional<Order> orderOptional = this.orderRepository.findById(orderId); 
		if (orderOptional.isPresent()) {
			return orderOptional.get();
		}
		throw new OrderException("Order not exist with id "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(long userId) {
		List<Order> orders = this.orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setPaymentStatus("COMPLETED");;
		return order;
	}

	@Override
	public Order confirmedOrder(long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return this.orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return this.orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return this.orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELED");
		return this.orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {  
		return this.orderRepository.findAll();
	}

	@Override
	public void deleteOrder(long orderId) throws OrderException {
		this.orderRepository.deleteById(orderId);
	}

}
