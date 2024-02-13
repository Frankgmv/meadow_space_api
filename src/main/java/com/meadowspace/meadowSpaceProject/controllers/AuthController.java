package com.meadowspace.meadowSpaceProject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meadowspace.meadowSpaceProject.config.JwtService;
import com.meadowspace.meadowSpaceProject.data.AuthResponse;
import com.meadowspace.meadowSpaceProject.data.AuthenticateRequest;
import com.meadowspace.meadowSpaceProject.data.RegisterRequest;
import com.meadowspace.meadowSpaceProject.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;
	private JwtService jwtService;
	
	public AuthController(AuthService authService, JwtService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticateRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	
	@PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            jwtService.invalidateToken(jwt);
            return ResponseEntity.ok("Logout exitoso");
        } else {
            return ResponseEntity.badRequest().body("No se proporcionó un token de autorización");
        }
    }
	
}
