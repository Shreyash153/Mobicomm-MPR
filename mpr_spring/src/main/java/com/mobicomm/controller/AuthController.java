package com.mobicomm.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobicomm.dto.AuthenticationRequest;
import com.mobicomm.dto.AuthenticationResponse;
import com.mobicomm.dto.SignupRequest;
import com.mobicomm.dto.UserDto;
import com.mobicomm.entity.User;
import com.mobicomm.repository.UserRepository;
import com.mobicomm.services.auth.AuthService;
import com.mobicomm.services.jwt.UserService;
import com.mobicomm.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {


	private final AuthService authService;
	private final UserService userService;
	private final JWTUtil jwtUtil;
	private final UserRepository userRepository;
	
	
	@Autowired
	 public AuthController(AuthService authService, UserService userService, UserRepository userRepository, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
	        this.authService = authService;
			this.userService = userService;
			this.jwtUtil = jwtUtil;
			this.userRepository = userRepository;
			
			
	    }
	 
	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
		
		if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
			return new ResponseEntity<>("Customer already exists with this email !",HttpStatus.NOT_ACCEPTABLE);
		UserDto createdCustomerDto = authService.createCustomer(signupRequest);
		
		if(createdCustomerDto == null) return new ResponseEntity<>("Customer not created",HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(createdCustomerDto,HttpStatus.CREATED);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
	    String email = authenticationRequest.getEmail();
	    String password = authenticationRequest.getPassword();
	    
	    
	    Optional<User> optionalUser = userRepository.findFirstByEmail(email);
	    System.out.println(optionalUser.get().getPassword());
	    
	    if (optionalUser.isPresent() && passwordMatches(optionalUser.get().getPassword(), password)) {
	    	final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
	        String jwt = jwtUtil.generateToken(userDetails);

	        AuthenticationResponse authenticationResponse = new AuthenticationResponse(
	                jwt,
	                optionalUser.get().getId(),
	                optionalUser.get().getUserRole(),
	                optionalUser.get().getName(),
	                optionalUser.get().getPhone()
	        );

	        return ResponseEntity.ok(authenticationResponse);
	        
	    } else {
	        System.out.println("Invalid username or password !");
	        return new ResponseEntity("Invalid username or password !",HttpStatus.BAD_REQUEST);
	    }
	}

	private boolean passwordMatches(String string, String password) {
	    // Implement your logic to check if the provided password matches the stored password
	    // You might use a password encoder or other mechanism depending on your setup.
	    // For example, you can use Spring Security's PasswordEncoder.matches method.

	    // Assuming you have a password encoder bean in your configuration:
	    // return passwordEncoder.matches(password, user.getPassword());

	    // For demonstration purposes, a simple equality check is used:
		boolean flag=false;
	    if(string.equals(password)) {
	    	flag=true;
	    	System.out.println("login success !");
	    };
	    
	    return flag;
	}

	
	
	
}
