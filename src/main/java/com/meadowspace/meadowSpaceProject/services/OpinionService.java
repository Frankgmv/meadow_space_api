package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.data.dataOpinionAndMult;
import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;
import com.meadowspace.meadowSpaceProject.entity.MultimediaProperty;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.repositories.ICustomerOpinionRepository;
import com.meadowspace.meadowSpaceProject.repositories.IPropertyRepository;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;

@Service
public class OpinionService {

	ICustomerOpinionRepository customerOpinionRepository;
	IUserRepository userRepository;
	IPropertyRepository propertyRepository;
	
	public OpinionService(ICustomerOpinionRepository customerOpinionRepository,
			IUserRepository userRepository,
			IPropertyRepository propertyRepository) {
		this.customerOpinionRepository = customerOpinionRepository;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
	}

	public List<CustomerOpinion> consultarOpiniones(String propertyId){
		return customerOpinionRepository.consultarComentario(propertyId);
	}
	
	public Optional<CustomerOpinion> consultarOpinionById(String id){
		return customerOpinionRepository.findById(id);
	}
	
	public List<CustomerOpinion> consultarTodasLasOpiniones(){
		return customerOpinionRepository.findAll();
	}

	public void crearCommentOpinion(dataOpinionAndMult data) throws Exception {
		
		Optional<User> existeUser = userRepository.findById(data.getUser().getId());
		Optional<Property> existeProperty = propertyRepository.findById(data.getProperty().getId());
		
		if(!existeUser.isPresent()) {
			throw new Exception("El usuario fue mal insertado");
		}
		
		if(!existeProperty.isPresent()) {
			throw new Exception("La propiedad fue mal insertada");
		}

		if(data.getComment() != null || data.getPicture() != null) {
			CustomerOpinion comment = new CustomerOpinion();
			
			comment.setComment(data.getComment());
			comment.setUser(data.getUser());
			comment.setPicture(data.getPicture());
			comment.setProperty(data.getProperty());
			
			customerOpinionRepository.save(comment);
		}else {
			throw new Exception("Error en la insercion de datos");
		}
	}
	
	public Optional<CustomerOpinion> obtenerById (String id){
		return customerOpinionRepository.findById(id);
	}
	
	public void deleteCommentById(String id) throws Exception {
		Optional<CustomerOpinion> existeOpinion = customerOpinionRepository.findById(id);
		
		if(!existeOpinion.isPresent()) {
			throw new Exception("Archivo no encontrado");
		}
		customerOpinionRepository.delete(existeOpinion.get());
	}
}
