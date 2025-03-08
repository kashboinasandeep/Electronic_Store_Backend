package com.electronicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicstore.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
}

