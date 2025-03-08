package com.electronicstore.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.electronicstore.dto.GoogleLoginRequest;
import com.electronicstore.dto.JwtRequest;
import com.electronicstore.dto.JwtResponse;
import com.electronicstore.dto.RefreshTokenDto;
import com.electronicstore.dto.RefreshTokenRequest;
import com.electronicstore.dto.UserDto;
import com.electronicstore.entity.Providers;
import com.electronicstore.entity.User;
import com.electronicstore.exceptions.BadApiRequestException;
import com.electronicstore.exceptions.ResourceNotFoundException;
import com.electronicstore.security.JwtHelper;
import com.electronicstore.service.RefreshTokenService;
import com.electronicstore.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/auth")
@Api(value = "AuthController",description = "API For Authentication!!!")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Value("${app.google.clinet_id}")
	
	private String googleClientId;
	
	@Value("${app.google.default_password}")
	private String googleProviderDefaultPassword;
		
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@PostMapping("/regenerate-token")
	
	public ResponseEntity<JwtResponse>regenerateToken(@RequestBody RefreshTokenRequest request)
	{
		RefreshTokenDto refreshTokenDto = refreshTokenService.findByToken(request.getRefreshToken());
		RefreshTokenDto refreshTokenDto2 = refreshTokenService.verifyRefreshToken(refreshTokenDto);
		UserDto user = refreshTokenService.getUser(refreshTokenDto2);
		String jwtToken = jwtHelper.generateToken(mapper.map(user, User.class));
		
		JwtResponse jwtResponse = JwtResponse.builder()
				.token(jwtToken)
				.refreshToken(refreshTokenDto)
				.user(user)
				.build();
		
		return ResponseEntity.ok(jwtResponse);
		
	}
	
	
	
	
	
	
	
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> login(@RequestBody  JwtRequest request){
		
		logger.info("email {} , password {}",request.getEmail(),request.getPassword());
		
		this.doAuthenctication(request.getEmail(),request.getPassword());
		
		User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
	
		//generate Token
		String token = jwtHelper.generateToken(user);
		
		RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
		
		//send the response(both token and user)
		JwtResponse jwtResponse = JwtResponse.builder()
				.token(token)
				.user(mapper.map(	user, UserDto.class))
				.refreshToken(refreshToken)
				.build();
		
		
		return ResponseEntity.ok(jwtResponse);
	}
	
	

	private void doAuthenctication(String email, String passowrd) {
		
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, passowrd);
			
			authenticationManager.authenticate(authentication);
			
		}catch(BadCredentialsException ex) {
			throw new BadCredentialsException("invalid username and password !!!");
		}
		
	}
	
	//handle login with google
	//{idToken}
	
	@PostMapping("/login-with-google")
	public ResponseEntity<JwtResponse>handleGoogleLogin(@RequestBody GoogleLoginRequest loginRequest) throws GeneralSecurityException, IOException{
		
		logger.info("id Token {}",loginRequest.getIdToken());
		
		//token verify
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(),new GsonFactory()).setAudience(List.of(googleClientId)).build();
		
		GoogleIdToken googleIdToken = verifier.verify(loginRequest.getIdToken());
		
		
		if(googleIdToken!=null) {
			//token verified 
			Payload payload = googleIdToken.getPayload();
			
			String email = payload.getEmail();
			String username = payload.getSubject();
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			String locale = (String) payload.get("locale");
			String familyName = (String) payload.get("family_name");
			String givenName = (String) payload.get("given_name"); 
			
			logger.info("Name {}",name);
			logger.info("Email {}",email);
			logger.info("PictireUrl {}",pictureUrl);
			logger.info("username {}",username);
			
			
			UserDto userDto = new UserDto();
			userDto.setName(name);
			userDto.setEmail(email);
			userDto.setImageName(pictureUrl);
			userDto.setPassword(googleProviderDefaultPassword);
			userDto.setAbout("user is created using google");
			userDto.setProvider(Providers.GOOGLE);
			
			UserDto user = null;
			
			
			try {
				
				logger.info("user is loaded from database");
				
				 user = userService.getUserByEmail(userDto.getEmail());	
				 
				 if(user.getProvider().equals(userDto.getProvider())){
					 
				 }else {
					 throw new BadCredentialsException("Your Email is Already Registered Login through using username and password");
				 }
				 
			}
			catch(ResourceNotFoundException ex) {
				
				logger.info("this time user created beacuse  this is new user");
				
				 user = userService.createUser(userDto);
				
			}
			
			
			this.doAuthenctication(user.getEmail(),userDto.getPassword());
			
			
			User user1 = mapper.map(user,User.class);
			
			//generate Token
			String token = jwtHelper.generateToken(user1);
				
			//send the response(both token and user)
			JwtResponse jwtResponse = JwtResponse.builder().token(token).user(user).build();
			
			
			return ResponseEntity.ok(jwtResponse);
		}
			
			
			
		else {
			logger.info("token is invalid !!!");
			throw new BadApiRequestException("Invalid Google User !!!");
		}
		
		
	}
	
	
}
