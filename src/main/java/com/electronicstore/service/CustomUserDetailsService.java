package com.electronicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.electronicstore.exceptions.ResourceNotFoundException;
import com.electronicstore.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		return userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user not found with given username"));
	}

}
