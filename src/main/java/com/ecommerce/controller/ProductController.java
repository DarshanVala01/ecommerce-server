package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	// Example of filter products
	// BASE_URL/api/products?category=cat&color=value&size=M............other all like this
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, 
			@RequestParam List<String> color, @RequestParam List<String> size,@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice,@RequestParam Integer minDiscount,@RequestParam String sort,
			@RequestParam String stock,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
	    
	    Page<Product> response = this.productService.getAllProduct(
	            category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
	    
	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable long productId) throws ProductException{
		Product product = this.productService.findProductById(productId);
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
	}
}
