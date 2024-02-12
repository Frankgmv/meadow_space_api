package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.entity.Role;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private final IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void crearUser(Long id, String name, String surname, String email, String phone, String cellphone,
			String address, String picture, String password, Role rol) throws Exception {

		validarUsuario(id, name, surname, email, phone, cellphone, address, password);

		Optional<User> existingUser = userRepository.findById(id);
		if (existingUser.isPresent()) {
			throw new Exception("El usuario con el ID " + id + " ya existe en la base de datos.");
		}

		List<User> existingEmail = userRepository.buscarPorEmail(email);

		if (existingEmail.size() != 0) {
			throw new Exception("El email " + email + " ya existe en la base de datos.");
		}

		User user = new User();

		user.setId(id);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setPhone(phone);
		user.setCellphone(cellphone);
		user.setAddress(address);
		user.setPicture(picture);
		user.setRol(rol);
		user.setPassword(password);

		userRepository.save(user);
	}

	public List<User> listarUser() {
		return userRepository.findAll();
	}

	public Optional<User> obtenerUsuarioPorId(Long id) {
		return userRepository.findById(id);
	}

	@Transactional
	public User updateUser(Long id, String name, String surname, String email, String phone, String cellphone,
			String address, Role rol, String password) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new Exception("Usuario con ID " + id + " no encontrado.");
		}

		User updatedUser = user.get();
		updatedUser.setName(name);
		updatedUser.setSurname(surname);
		updatedUser.setEmail(email);
		updatedUser.setPhone(phone);
		updatedUser.setCellphone(cellphone);
		updatedUser.setAddress(address);
		updatedUser.setPassword(password);
		updatedUser.setRol(rol);

		return userRepository.save(updatedUser);
	}

	@Transactional
	public User updateUserPicture(Long id, String picture) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new Exception("Usuario con ID " + id + " no encontrado.");
		}

		User updatedUser = user.get();
		updatedUser.setPicture(picture);

		return userRepository.save(updatedUser);
	}

	public void deleteUser(Long id) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new Exception("Usuario con ID " + id + " no encontrado.");
		}
		userRepository.delete(user.get());
	}

	private void validarUsuario(Long id, String name, String surname, String email, String phone, String cellphone,
			String address, String password) throws Exception {
		if (id == null) {
			throw new Exception(" id no puede ser nulo !!!");
		}

		if (name == null || name.isEmpty()) {
			throw new Exception(" Nombre no puede ser vacío o nulo !!!");
		}

		if (surname == null || surname.isEmpty()) {
			throw new Exception(" Apellido no puede ser vacío o nulo !!!");
		}

		if (email == null || email.isEmpty()) {
			throw new Exception(" Correo no puede ser vacío o nulo !!!");
		}

		if (cellphone == null || cellphone.isEmpty()) {
			throw new Exception(" Celular no puede ser vacío o nulo !!!");
		}

		if (address == null || address.isEmpty()) {
			throw new Exception(" Dirección no puede ser vacío o nulo !!!");
		}

		if (password == null || password.isEmpty()) {
			throw new Exception(" Contraseña no puede ser vacío o nulo !!!");
		}
	}

}
