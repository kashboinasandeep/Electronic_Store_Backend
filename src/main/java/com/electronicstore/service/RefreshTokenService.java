package com.electronicstore.service;

import com.electronicstore.dto.RefreshTokenDto;
import com.electronicstore.dto.UserDto;
import com.electronicstore.entity.User;

public interface RefreshTokenService {
	
	//create
	RefreshTokenDto createRefreshToken(String usernamae);
	
	
	//findby by token
	RefreshTokenDto findByToken(String token);
	
	
	//verify token
	RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenDto);
	
	//get_User_Data
	UserDto getUser(RefreshTokenDto dto); 

}
