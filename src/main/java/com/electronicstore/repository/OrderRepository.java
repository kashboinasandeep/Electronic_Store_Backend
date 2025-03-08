package com.electronicstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicstore.entity.Order;
import com.electronicstore.entity.User;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);

}
