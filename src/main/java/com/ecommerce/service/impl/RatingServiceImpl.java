package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Rating;
import com.ecommerce.model.User;
import com.ecommerce.repository.RatingRepository;
import com.ecommerce.request.RatingRequest;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService{

	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ProductService productService;
	
	@Override
	public Rating createRating(RatingRequest request, User user) throws ProductException {
		Product product = this.productService.findProductById(request.getProductId());
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(request.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		Rating createdRating = this.ratingRepository.save(rating);
		return createdRating;
	}

	@Override
	public List<Rating> getProducts(long productId) {
		List<Rating> getAllProductsRatings = this.ratingRepository.getAllProductsRatings(productId);
		return getAllProductsRatings;
	}

}
