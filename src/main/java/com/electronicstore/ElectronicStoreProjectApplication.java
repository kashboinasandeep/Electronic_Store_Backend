package com.electronicstore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.electronicstore.config.AppConstants;
import com.electronicstore.entity.Role;
import com.electronicstore.entity.User;
import com.electronicstore.repository.RoleRepository;
import com.electronicstore.repository.UserRepository;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreProjectApplication.class, args);
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		
		//=====================================================================CREATING ROLES=========================================================
		
		Role roleAdmin = roleRepository.findByName("ROLE_"+AppConstants.ROLE_ADMIN).orElse(null);
		if (roleAdmin == null) {
		    Role r1 = new Role();
		    r1.setRoleId("wetrsdfwetwfasfwdf");
		    r1.setName("ROLE_"+AppConstants.ROLE_ADMIN);
		    roleAdmin = roleRepository.save(r1); // ✅ Save and reassign
		}
		
		Role roleNormal = roleRepository.findByName("ROLE_"+AppConstants.ROLE_NORMAL).orElse(null);
		if (roleNormal == null) {
		    Role r2 = new Role();
		    r2.setRoleId(UUID.randomUUID().toString());
		    r2.setName("ROLE"+AppConstants.ROLE_NORMAL);
		    roleNormal = roleRepository.save(r2); // ✅ Save and reassign
		}

		User user = userRepository.findByEmail("nagaraju@gmail.com").orElse(null);
		if (user == null) {
		    user = new User();
		    user.setUserId(UUID.randomUUID().toString());
		    user.setName("nagaraju");
		    user.setEmail("nagaraju@gmail.com");
		    user.setPassword(passwordEncoder.encode("nagaraju"));
		    user.setGender("male");
		    user.setAbout("admin");
		    user.setImageName("nagaraju.png");

		    if (roleAdmin != null) {
		        user.setRoles(List.of(roleAdmin)); // ✅ Avoid NullPointerException
		    } else {
		        System.out.println("Error: roleAdmin is null!");
		    }

		    userRepository.save(user);
		}


	}}
