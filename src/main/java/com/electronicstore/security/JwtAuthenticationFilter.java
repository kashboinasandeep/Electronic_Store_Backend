package com.electronicstore.security;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		
		
		
		//step:1 ---> GET HEADER
		
		     	//retrive the header
				//Authorization: Bearer hdhjdkjsieuddnnj---->header
		
			String requestHeader = request.getHeader("Authorization");
			logger.info("Header{}",requestHeader);
			
			
			
			//STEP2: GET 
			
			String username=null;
			
			String token=null;
			
			if(requestHeader!=null && requestHeader.startsWith("Bearer"))
			{
			
				//removing the bearer from token
				 token = requestHeader.substring(7);
				 
				 //use the method getUsernameFromToken to get username from token
				 //we get many error write code in try catch blocks
				 
				 	try {
				 		 //use the method getUsernameFromToken to get username from token
						 username = jwtHelper.getUsernameFromToken(token);
						 
						 logger.info("token username:{}",username);

				 	}catch (IllegalArgumentException ex) {
						
				 		logger.info("Illegal Argument while fetching usename"+ex.getMessage());
				 		
					}catch (ExpiredJwtException ex) {
					
						logger.info("Given Jwt is Expired"+ex.getMessage());
						
					}catch (MalformedJwtException ex) {
						
						logger.info("Some Changes Has been done in the token!!! Invalid token"+ex.getMessage());
					}catch (Exception ex) {
						
						ex.printStackTrace();
					}
				 	
				 		
				}else {
					
				logger.info("Invalid Header!!!  Header is not starting with Bearer");
				
				}
			
			
			 //if username is not NULL and Authentication is NOT DONE  we use this line of code
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				//username there and 
										//   authentication is null
				 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				 
				 //validate token
				 
				 if(username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token)) 
				 {
					
					  //token valid
					 
					 //under securityContext we have to perform authentication
					 
					 //
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
					SecurityContextHolder.getContext().setAuthentication(authentication);
				 }
			}
			
			
		filterChain.doFilter(request, response);
			
				
	}

}
