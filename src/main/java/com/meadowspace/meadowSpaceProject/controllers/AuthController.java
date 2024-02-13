package com.meadowspace.meadowSpaceProject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meadowspace.meadowSpaceProject.data.AuthResponse;
import com.meadowspace.meadowSpaceProject.data.AuthenticateRequest;
import com.meadowspace.meadowSpaceProject.data.RegisterRequest;
import com.meadowspace.meadowSpaceProject.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticateRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

}
