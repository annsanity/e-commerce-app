package com.ecommerce.projectapp.response;

import com.ecommerce.projectapp.domain.USER_ROLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	private String jwt;
	
	private boolean status;
	
	private String message;

	private USER_ROLE role;
}