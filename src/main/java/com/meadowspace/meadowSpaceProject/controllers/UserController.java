package com.meadowspace.meadowSpaceProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meadowspace.meadowSpaceProject.data.DataUser;
import com.meadowspace.meadowSpaceProject.entity.Role;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.exceptions.MyError;
import com.meadowspace.meadowSpaceProject.services.UserService;
import com.meadowspace.meadowSpaceProject.services.img.UploadFilesService;

@RestController
@RequestMapping("/data")
public class UserController {
	private final UserService userService;
	UploadFilesService uploadFileService;

	@Autowired
	public UserController(UserService userService, UploadFilesService uploadFileService) {
		this.userService = userService;
		this.uploadFileService = uploadFileService;
	}

	@GetMapping("/user")
	public ResponseEntity<List<User>> obtenerUsuario() {
		List<User> listUser = userService.listarUser();
		return new ResponseEntity<>(listUser, HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> obtenerUsuarioById(@PathVariable Long id) {
		Optional<User> user = userService.obtenerUsuarioPorId(id);

		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUser(@ModelAttribute DataUser dataUser,
			@RequestParam(required = false) MultipartFile imagen) {
		try {
			if (imagen != null && !imagen.isEmpty()) {
				String uploadedFileUrl = uploadFileService.handleFileUpload(imagen);
				dataUser.setPicture(uploadedFileUrl);
			}
			Role role = Role.valueOf(dataUser.getRol().toString());
			dataUser.setRol(role);

			userService.crearUser(dataUser.getId(), dataUser.getName(), dataUser.getSurname(), dataUser.getEmail(),
					dataUser.getPhone(), dataUser.getCellphone(), dataUser.getAddress(), dataUser.getPicture(),
					dataUser.getPassword(), dataUser.getRol());

			return new ResponseEntity<>("usuario registrado", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/user/{id}", consumes = { MediaType.ALL_VALUE })
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody DataUser dataUser) throws Exception {
		try {
		
			Role role = Role.valueOf(dataUser.getRol().toString());
			dataUser.setRol(role);

			User updatedUser = userService.updateUser(id, dataUser.getName(), dataUser.getSurname(),
					dataUser.getEmail(), dataUser.getPhone(), dataUser.getCellphone(), dataUser.getAddress(),
					dataUser.getPicture(), dataUser.getRol(), dataUser.getPassword());
			return new ResponseEntity<>(updatedUser.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	/*
	 * va en update
	 * 
	 * if (imagen != null && !imagen.isEmpty()) { String uploadedFileUrl =
	 * uploadFileService.handleFileUpload(imagen);
	 * 
	 * dataUser.setPicture(uploadedFileUrl); } Role role =
	 * Role.valueOf(dataUser.getRol().toString()); dataUser.setRol(role);
	 * 
	 * User updatedUser = userService.updateUser(id, dataUser.getName(),
	 * dataUser.getSurname(), dataUser.getEmail(), dataUser.getPhone(),
	 * dataUser.getCellphone(), dataUser.getAddress(), dataUser.getPicture(),
	 * dataUser.getRol(), dataUser.getPassword()); return new
	 * ResponseEntity<>(updatedUser, HttpStatus.OK);
	 */

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) throws MyError {
		userService.deleteUser(id);
		return new ResponseEntity<>("Usuario Eliminado", HttpStatus.OK);
	}

}
