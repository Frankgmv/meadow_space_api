package com.meadowspace.meadowSpaceProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
			String address, String password) throws MyError  {
		
		validarUsuario(id, name, surname, email, phone, cellphone, address, password);
		
		User user = new User();
		
		user.setId(id);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setPhone(phone);
		user.setCellphone(cellphone);
		user.setAddress(address);
		// user.setPicture(picture);
		user.setPassword(password);
		
		userRepository.save(user);
	}
	
	public List<User> listarUser() {
        return userRepository.findAll();
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
