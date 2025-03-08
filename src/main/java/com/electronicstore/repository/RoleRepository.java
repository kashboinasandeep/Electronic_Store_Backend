package com.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicstore.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	
	Optional<Role>findByName(String name);

}
