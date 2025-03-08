package com.electronicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicstore.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String>
{
}
