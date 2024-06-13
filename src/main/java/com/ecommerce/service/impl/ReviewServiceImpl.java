package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.request.ReviewRequest;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;
	

	@Override
	public Review createReview(ReviewRequest request, User user) throws ProductException {
		Product product = this.productService.findProductById(request.getProductId());
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(request.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		Review createdReview = this.reviewRepository.save(review);
		return createdReview;
	}

	@Override
	public List<Review> getAllReview(long productId) {
		List<Review> getAllProductsReview = this.reviewRepository.getAllProductsReview(productId);
		return getAllProductsReview;
	}

}
