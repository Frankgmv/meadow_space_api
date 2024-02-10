package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.data.dataOpinionAndMult;
import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;
import com.meadowspace.meadowSpaceProject.entity.MultimediaOpinions;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.repositories.ICustomerOpinionRepository;
import com.meadowspace.meadowSpaceProject.repositories.IMultimediaOpinionRepository;
import com.meadowspace.meadowSpaceProject.repositories.IPropertyRepository;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;

@Service
public class OpinionAndMultService {

	IMultimediaOpinionRepository multimediaOpinionRepository;
	ICustomerOpinionRepository customerOpinionRepository;
	IUserRepository userRepository;
	IPropertyRepository propertyRepository;
	
	
	
	public OpinionAndMultService(IMultimediaOpinionRepository multimediaOpinionRepository,
			ICustomerOpinionRepository customerOpinionRepository, IUserRepository userRepository,
			IPropertyRepository propertyRepository) {
		this.multimediaOpinionRepository = multimediaOpinionRepository;
		this.customerOpinionRepository = customerOpinionRepository;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
	}

	public List<CustomerOpinion> consultarOpiniones(Long userId, String propertyId){
		return customerOpinionRepository.consultarComentario(userId, userId);
	}

	public void crearCommentOpinion(dataOpinionAndMult data) throws Exception {
		if(data.getComment() != null) {
			CustomerOpinion comment = new CustomerOpinion();
			
			comment.setComment(data.getComment());
			comment.setUser(data.getUser());
			comment.setProperty(data.getProperty());
			
			customerOpinionRepository.save(comment);
		}
	}

	public void crearMultimediaOpinion(dataOpinionAndMult data) throws Exception {
		if(data.getPicture() != null) {
			Optional<User> existeUser = userRepository.findById(data.getUser().getId());
			Optional<Property> existeProperty = propertyRepository.findById(data.getProperty().getId());
			
			if(!existeUser.isPresent()) {
				throw new Exception("El usuario fue mal insertado");
			}
			
			if(!existeProperty.isPresent()) {
				throw new Exception("La propiedad fue mal insertada");
			}
			
			MultimediaOpinions multOpinion = new MultimediaOpinions();
			
			multOpinion.setPicture(data.getPicture());
			multOpinion.setUser(data.getUser());
			multOpinion.setProperty(data.getProperty());
			
			multimediaOpinionRepository.save(multOpinion);
		}
	}
}
