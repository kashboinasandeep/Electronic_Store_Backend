package com.electronicstore.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class RefreshTokenDto {
	
	
	private int id;
	
	private String token;
	
	private Instant expiryDate;
	
	


}
