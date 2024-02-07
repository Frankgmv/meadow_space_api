package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.entity.Role;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.exceptions.MyError;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;

@Service
public class UserService {
	@Autowired
	private final IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void crearUser(Long id, String name, String surname, String email, String phone, String cellphone,
			String address, String picture, String password, Role rol) throws Exception {
		
		validarUsuario(id, name, surname, email, phone, cellphone, address, password);
		
		Optional<User> existingUser = userRepository.findById(id);
	    if (existingUser.isPresent()) {
	        throw new Exception("El usuario con el ID " + id + " ya existe en la base de datos.");
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
	
	public void deleteUser(Long id) throws MyError {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new MyError("Usuario con ID " + id + " no encontrado.");
        }
        userRepository.delete(user.get());
    }

    public User updateUser(Long id, String name, String surname, String email, String phone, String cellphone,
                           String address, String picture, Role rol, String password) throws MyError {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new MyError("Usuario con ID " + id + " no encontrado.");
        }

        User updatedUser = user.get();
        updatedUser.setName(name);
        updatedUser.setSurname(surname);
        updatedUser.setEmail(email);
        updatedUser.setPhone(phone);
        updatedUser.setCellphone(cellphone);
        updatedUser.setAddress(address);
        updatedUser.setPicture(picture);
        updatedUser.setRol(rol);
        updatedUser.setPassword(password);

        return userRepository.save(updatedUser);
    }
	
	private void validarUsuario(Long id, String name, String surname, String email, String phone, String cellphone,
			String address, String password) throws MyError {
		if (id == null) {
			throw new MyError(" id no puede ser nulo !!!");
		}

		if (name == null || name.isEmpty()) {
			throw new MyError(" Nombre no puede ser vacío o nulo !!!");
		}

		if (surname == null || surname.isEmpty()) {
			throw new MyError(" Apellido no puede ser vacío o nulo !!!");
		}

		if (email == null || email.isEmpty()) {
			throw new MyError(" Correo no puede ser vacío o nulo !!!");
		}
		
		if (cellphone == null || cellphone.isEmpty()) {
			throw new MyError(" Celular no puede ser vacío o nulo !!!");
		}
		
		if (address == null || address.isEmpty()) {
			throw new MyError(" Dirección no puede ser vacío o nulo !!!");
		}
		
		if (password== null || password.isEmpty()) {
			throw new MyError(" Contraseña no puede ser vacío o nulo !!!");
		}
	}
	
}
