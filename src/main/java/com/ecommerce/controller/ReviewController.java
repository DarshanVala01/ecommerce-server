package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.request.ReviewRequest;
import com.ecommerce.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request) throws UserException, ProductException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        User user = this.userRepository.findByEmail(email);
		Review review = this.reviewService.createReview(request, user);
		return new ResponseEntity<Review>(review,HttpStatus.CREATED);
	}
	
	@PostMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getAllReviews(@PathVariable("productId") Long productId) throws UserException, ProductException{
		List<Review> reviews = this.reviewService.getAllReview(productId);
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.ACCEPTED);
	}
}
