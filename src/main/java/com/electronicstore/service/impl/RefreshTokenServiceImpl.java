package com.electronicstore.service.impl;

import java.time.Instant;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronicstore.dto.RefreshTokenDto;
import com.electronicstore.dto.UserDto;
import com.electronicstore.entity.RefreshToken;
import com.electronicstore.entity.User;
import com.electronicstore.exceptions.ResourceNotFoundException;
import com.electronicstore.repository.RefreshTokenRepository;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

	
	private UserRepository userRepository;
	
	
	private RefreshTokenRepository refreshTokenRepository;
	
	private ModelMapper modelMapper;
	

	//CONSTRUCTOR
	public RefreshTokenServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
			ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.modelMapper = modelMapper;
	}
	

	
	

	@Override
	public RefreshTokenDto createRefreshToken(String usernamae) {
		//get user
		User user = userRepository.findByEmail(usernamae).orElseThrow(()-> new ResourceNotFoundException("username not found in database"));
		
		RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElse(null);
		
		//refresh token build karo
		if(refreshToken==null) 
		{
			
		refreshToken= RefreshToken.builder()
		.user(user)
		.token(UUID.randomUUID().toString())
		.expiryDate(Instant.now().plusSeconds(5*24*60*60))
		.build();
		
	}else {
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(Instant.now().plusSeconds(5*24*60*60));
		
	}
		RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
		return this.modelMapper.map(savedToken, RefreshTokenDto.class);
		
		
	}
		
	

	

	

	@Override
	public RefreshTokenDto findByToken(String token) {
		
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(()-> new ResourceNotFoundException("resfresh token not found in the database"));
		return this.modelMapper.map(refreshToken, RefreshTokenDto.class);
	}

	
	@Override
	public RefreshTokenDto  verifyRefreshToken(RefreshTokenDto token) {
		
		RefreshToken refreshToken = modelMapper.map(token,RefreshToken.class);
		
		if(token.getExpiryDate().compareTo(Instant.now())<0)
		{
			refreshTokenRepository.delete(refreshToken);
			
			throw new RuntimeException("Refresh Token Expired!!!!");
		}

		
		return token;
		
		
	}



	@Override
	public UserDto getUser(RefreshTokenDto dto) {
	RefreshToken refreshToken = refreshTokenRepository.findByToken(dto.getToken()).orElseThrow(()->new ResourceNotFoundException("toke not found"));
	User user = refreshToken.getUser();
		return modelMapper.map(user, UserDto.class);
	}

}
