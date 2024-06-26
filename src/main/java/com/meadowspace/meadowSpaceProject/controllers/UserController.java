package com.meadowspace.meadowSpaceProject.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.entity.Role;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.services.UserService;
import com.meadowspace.meadowSpaceProject.services.img.UploadFilesService;
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

@RestController
@RequestMapping("/data")
public class UserController {
	private final UserService userService;
	UploadFilesService uploadFileService;

	public UserController(UserService userService, UploadFilesService uploadFileService) {
		this.userService = userService;
		this.uploadFileService = uploadFileService;
	}

	@GetMapping("/user")
	public ResponseEntity<ResponseFormat> obtenerUsuario() {
		List<User> listUser = userService.listarUser();
		return ApiControllerUtil.buildResponse(listUser, HttpStatus.OK, true, "Lista de usuarios");
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<ResponseFormat> obtenerUsuarioById(@PathVariable Long id) {
		Optional<User> user = userService.obtenerUsuarioPorId(id);

		if (user.isPresent()) {
			return ApiControllerUtil.buildResponse(user, HttpStatus.OK, true, "Usuarios obtenido");
		} else {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, "Usuarios no encontrado");
		}
	}

	@PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseFormat> postUser(@ModelAttribute DataUser dataUser) {
		try {
			if (dataUser.getImagen() != null && !dataUser.getImagen().isEmpty()) {
				String uploadedFileUrl = uploadFileService.handleFileUpload(dataUser.getImagen());
				dataUser.setPicture(uploadedFileUrl);
			}

			Role role = Role.valueOf(dataUser.getRol().toString());
			dataUser.setRol(role);

			userService.crearUser(dataUser.getId(), dataUser.getName(), dataUser.getSurname(), dataUser.getEmail(),
					dataUser.getPhone(), dataUser.getCellphone(), dataUser.getAddress(), dataUser.getPicture(),
					dataUser.getPassword(), dataUser.getRol());

			return ApiControllerUtil.buildResponse(dataUser.toString(), HttpStatus.CREATED, true, "Usuarios creado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}

	@PutMapping(value = "/user/{id}")
	public ResponseEntity<ResponseFormat> updateUser(@PathVariable Long id, @RequestBody DataUser dataUser) throws Exception {
		try {
			if (dataUser.getRol() != null) {
				Role role = Role.valueOf(dataUser.getRol().toString());
				dataUser.setRol(role);
			}
			userService.updateUser(id, dataUser.getName(), dataUser.getSurname(),
					dataUser.getEmail(), dataUser.getPhone(), dataUser.getCellphone(), dataUser.getAddress(),
					dataUser.getRol(), dataUser.getPassword());

			return ApiControllerUtil.buildResponse(null, HttpStatus.CREATED, true, "Usuarios actualizado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@PutMapping("/user-picture/{id}")
	public ResponseEntity<ResponseFormat> updateUserPicture(@PathVariable Long id, @RequestParam(required = false) MultipartFile imagen) throws Exception {
		try {
			Optional<User> user = userService.obtenerUsuarioPorId(id);

			if (user.isPresent() && user.get().getPicture() != null) {
				uploadFileService.deleteFile(user.get().getPicture());
			}
			
			if (imagen != null) {
				String uploadedFileUrl = uploadFileService.handleFileUpload(imagen);			
				userService.updateUserPicture(id, uploadedFileUrl);
			}else {
				userService.updateUserPicture(id, null);				
			}

			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Foto de perfil actualizada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<ResponseFormat> deleteUser(@PathVariable Long id) throws IOException {
		try {
			Optional<User> user = userService.obtenerUsuarioPorId(id);

			userService.deleteUser(id);

			if (user.isPresent() && user.get().getPicture() != null) {
				uploadFileService.deleteFile(user.get().getPicture());
			}
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Usuario eliminado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}

}
