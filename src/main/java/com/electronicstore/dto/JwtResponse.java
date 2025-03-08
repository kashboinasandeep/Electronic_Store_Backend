package com.electronicstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
	
	private String token;
	
	private RefreshTokenDto refreshToken;

	UserDto user;

	
}
