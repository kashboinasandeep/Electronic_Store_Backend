package com.electronicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicstore.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}

