package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u from User u WHERE u.email= :email")
	public User findByEmail(@Param("email")String email);


}
