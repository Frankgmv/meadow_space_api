package com.meadowspace.meadowSpaceProject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meadowspace.meadowSpaceProject.config.JwtService;
import com.meadowspace.meadowSpaceProject.data.AuthenticateRequest;
import com.meadowspace.meadowSpaceProject.data.RegisterRequest;
import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.services.AuthService;
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;
	private JwtService jwtService;


	public AuthController(AuthService authService, JwtService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseFormat> register(@ModelAttribute RegisterRequest request) {
		try {
			var data = authService.register(request);

			return ApiControllerUtil.buildResponse(data, HttpStatus.CREATED, true, "Registro exitoso");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false,e.getMessage());
		}
	}

	@PostMapping("/authenticate")
	public ResponseEntity<ResponseFormat> authenticate(@RequestBody AuthenticateRequest request) {
		try {
			var data = authService.authenticate(request);
			return ApiControllerUtil.buildResponse(data, HttpStatus.OK, true, "Inicio exitoso");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<ResponseFormat> logout(HttpServletRequest request) {
		try {
			String authorizationHeader = request.getHeader("Authorization");

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String jwt = authorizationHeader.substring(7);
				jwtService.invalidateToken(jwt);
			} else {
				throw new Exception("No hay token");
			}
			
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Cierre de sesión exitoso");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false,e.getMessage());
		}
	}

}
