package com.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicstore.entity.Cart;
import com.electronicstore.entity.User;

public interface CartRepository extends JpaRepository<Cart, String> {


    Optional<Cart> findByUser(User user);

}
