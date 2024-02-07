package com.meadowspace.meadowSpaceProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meadowspace.meadowSpaceProject.services.UploadFilesService;
import com.meadowspace.meadowSpaceProject.services.UserService;

@RestController
@RequestMapping("/data")
public class UserController {
	private final UserService userService;
	UploadFilesService uploadFileService;
	
	@Autowired
	public UserController(UserService userService, 	UploadFilesService uploadFileService) {
		this.userService = userService;
		this.uploadFileService = uploadFileService;
	}
	
	@GetMapping("/user")
	public ResponseEntity<String> obtenerUsuario(){
		return ResponseEntity.ok("ALL USERS");
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<String> obtenerUsuarioById(@PathVariable Long id){
		return ResponseEntity.ok("USER BY ID "+id);
	}
	
	@PostMapping("/user")
	public ResponseEntity<String> postUser(MultipartFile picture) {
		try {
			
			return new ResponseEntity<>(uploadFileService.handleFileUpload(picture), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
}
