package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.request.CreateProductRequest;
import com.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
//	@Autowired
//	private UserService userService;
	
	
	@Override
	public Product createProduct(CreateProductRequest request) {
		
		Category topLevel = categoryRepository.findByName(request.getTopLevelCategory());
		System.out.println("Top Level Category First:"+topLevel);
		if (topLevel == null) {
			System.out.println("In the if condition");
			Category topLevelCategory = new Category();
			System.out.println("Top Level Category"+request.getTopLevelCategory());
			topLevelCategory.setName(request.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			topLevel = categoryRepository.save(topLevelCategory);
			System.out.println("Top Level Category Second:"+topLevel);
		}
		
		
		Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLevelCategory(),topLevel.getName());
		System.out.println("Second Level Category First:"+secondLevel);
		if (secondLevel == null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(request.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			
			secondLevel = categoryRepository.save(secondLevelCategory);
			System.out.println("Second Level Category Second:"+secondLevel);
		}
		
		
		Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(),secondLevel.getName());
		System.out.println("Third Level Category First:"+thirdLevel);
		if (thirdLevel == null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(request.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);
			
			thirdLevel = categoryRepository.save(thirdLevelCategory);
			System.out.println("Third Level Category Second:"+thirdLevel);
		}
		
		Product product = new Product();
		product.setTitle(request.getTitle());
		product.setColor(request.getColor());
		product.setDescription(request.getDescription());
		product.setDiscountedPrice(request.getDiscountedPrice());
		product.setDiscountPercent(request.getDiscountPercent());
		product.setImageUrl(request.getImageUrl());
		product.setBrand(request.getBrand());
		product.setPrice(request.getPrice());
		product.setSizes(request.getSizes());
		product.setQuantity(request.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct = this.productRepository.save(product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(long productId) throws ProductException {
		
		Product product = findProductById(productId);
		product.getSizes().clear();   // When we delete the product that time size must be creates error that while must be clear() the size before delete the product
		this.productRepository.delete(product);
		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(long productId, Product updateProduct) throws ProductException {
		Product product = findProductById(productId);
		if (updateProduct.getQuantity() != 0) {
			product.setQuantity(updateProduct.getQuantity());
		}
		
		Product savedProduct = this.productRepository.save(product);
		return savedProduct;
	}

	@Override
	public Product findProductById(long productId) throws ProductException {
		Optional<Product> product = this.productRepository.findById(productId);
		
		if (product.isPresent()) {
			return product.get();
		}
		throw new ProductException("Product not found with id :"+productId);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = this.productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		if (!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
		}
		
		if (stock!=null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
			}
			else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize() , products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filterProducts = new PageImpl<>(pageContent,pageable,products.size());
		return filterProducts;
	}

	@Override
	public List<Product> findAllProducts() {
		List<Product> findAllProduct = this.productRepository.findAll();
		return findAllProduct;
	}

}
