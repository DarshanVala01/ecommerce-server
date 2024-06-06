package com.ecommerce.request;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
	
	private String title;
	private String description;
	private int price;
	private int discountedPrice;
	private int discountPresent;
	private int quantity;
	private String brand;
	private String color;
	private Set<Size> sizes = new HashSet<>();
	private String imageUrl;
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
	
}
