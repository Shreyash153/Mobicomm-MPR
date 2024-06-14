package com.mobicomm.services.auth;


import com.mobicomm.dto.SignupRequest;
import com.mobicomm.dto.UserDto;

public interface AuthService {

	UserDto createCustomer(SignupRequest signupRequest);
	
	boolean hasCustomerWithEmail(String email);
}
