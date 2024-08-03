package com.mobicomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MprSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MprSpringApplication.class, args);
		
	}

}

//check port status- netstat -ano | findstr :8080
