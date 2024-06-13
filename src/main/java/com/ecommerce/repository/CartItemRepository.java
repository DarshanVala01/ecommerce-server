package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	@Query("SELECT c FROM CartItem c WHERE c.cart= :cart AND c.product= :product AND c.size= :size AND c.userId= :userId ")
	public CartItem isCartItemExist(@Param("cart") Cart cart,
									@Param("product") Product product,
									@Param("size") String size,
									@Param("userId") long userId);
}
