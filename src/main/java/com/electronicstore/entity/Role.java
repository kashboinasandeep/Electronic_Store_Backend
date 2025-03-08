package com.electronicstore.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter

public class Role {
	
	@Id
	private String roleId;
	private String name;
	
	
	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	  private List<User> users = new ArrayList<>();
	
	

}
