package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.OrderException;
import com.ecommerce.model.Order;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
		List<Order> orders = this.orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirm")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order = this.orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order = this.orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order = this.orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order = this.orderService.canceledOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId) throws OrderException{
		 this.orderService.deleteOrder(orderId);
		 
		 ApiResponse response = new ApiResponse();
		 response.setMessage("Order Deleted Successfully");
		 response.setStatus(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
