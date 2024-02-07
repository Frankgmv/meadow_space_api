package com.meadowspace.meadowSpaceProject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.meadowspace.meadowSpaceProject.services.UserService;

@RestController
@RequestMapping("/data")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user")
	public ResponseEntity<String> obtenerUsuario(){
		return ResponseEntity.ok("ALL USERS");
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<String> obtenerUsuarioById(@PathVariable Long id){
		return ResponseEntity.ok("USER BY ID "+id);
	}

	
}
