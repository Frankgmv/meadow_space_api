package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.config.JwtService;
import com.meadowspace.meadowSpaceProject.data.AuthResponse;
import com.meadowspace.meadowSpaceProject.data.AuthenticateRequest;
import com.meadowspace.meadowSpaceProject.data.RegisterRequest;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;
import com.meadowspace.meadowSpaceProject.services.img.UploadFilesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

	private final UploadFilesService uploadFilesService;
	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	// Validate and register user
	@Override
	public AuthResponse register(RegisterRequest request) throws Exception {
		Optional<User> existeUser = userRepository.findById(request.getId());
		List<User> existeEmail = userRepository.buscarPorEmail(request.getEmail());
		
		if(existeEmail.size() != 0 ) {
			throw new Exception("Correo "+ request.getEmail() + " ya existe.");
		}
		
		if(existeUser.isPresent()) {
			throw new Exception(request.getId() + " ya está en uso.");
		}
		//		Instanced user and fill fields
		User user = new User();

		user.setId(request.getId());
		user.setName(request.getName());
		user.setSurname(request.getSurname());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setCellphone(request.getCellphone());
		user.setAddress(request.getAddress());
		user.setPicture(request.getPicture());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRol(request.getRol());

		if (request.getImagen() != null) {
			String pathUrl = uploadFilesService.handleFileUpload(request.getImagen());
			user.setPicture(pathUrl);
		}

		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);

		AuthResponse response = new AuthResponse();
		response.setToken(jwtToken);
		return response;
	}

	@Override
	public AuthResponse authenticate(AuthenticateRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		AuthResponse response = new AuthResponse();
		response.setToken(jwtToken);
		return response;
	}

}
