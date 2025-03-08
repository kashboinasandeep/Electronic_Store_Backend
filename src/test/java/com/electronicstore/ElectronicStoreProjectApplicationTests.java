package com.electronicstore;



import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.electronicstore.entity.User;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.security.JwtHelper;


@SpringBootTest
class ElectronicStoreProjectApplicationTests {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtHelper jwtHelper;

    @Test
    void contextLoads() {
        
    }

    @Test
    void testTokenGeneration() {
        // Fetch user safely
        User user = userRepository.findByEmail("indu@gmail.com").get();
        
        String token = jwtHelper.generateToken(user);
        System.out.println(token);
        
        System.out.println("getting username from token");
        System.out.println(jwtHelper.getUsernameFromToken(token));
        
        
    }
}
