package com.ecommerce.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
	
	private String cardholderName;
	private String cardNumber;
	private LocalDate expirationDate;
	private String cvv;
}
