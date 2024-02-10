package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.data.DataMultimediaProperty;
import com.meadowspace.meadowSpaceProject.entity.MultimediaProperty;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.repositories.IMultimediaPropertyRepository;
import com.meadowspace.meadowSpaceProject.repositories.IPropertyRepository;

@Service
public class MultimediaPropertyService {

	IMultimediaPropertyRepository multimediaPropertyRespository;
	IPropertyRepository propertyRepository; 

	public MultimediaPropertyService(IMultimediaPropertyRepository multimediaPropertyRespository,
			IPropertyRepository propertyRepository) {
		this.multimediaPropertyRespository = multimediaPropertyRespository;
		this.propertyRepository = propertyRepository;
	}
	
	public void crearArchivo(DataMultimediaProperty dataMultimediaProperty) throws Exception {
		if(dataMultimediaProperty.getProperty() == null) {
			throw new Exception("La propiedad fue mal insertada");
		}
		
		Optional<Property> existePropiedad = propertyRepository.findById(dataMultimediaProperty.getProperty().getId());
		if(!existePropiedad.isPresent()) {
			throw new Exception("La propiedad no fue encontrada!!");
		}

		MultimediaProperty fileProperty = new MultimediaProperty();
		
		fileProperty.setPicture(dataMultimediaProperty.getPicture());
		fileProperty.setProperty(existePropiedad.get());
		
		multimediaPropertyRespository.save(fileProperty);
	}
	
	public List<MultimediaProperty> obtenerFilesByPropertyId(String id) {
		return multimediaPropertyRespository.findByPropertyId(id);
	}
	
	public Optional<MultimediaProperty> obtenerFileById(String id) {
		return multimediaPropertyRespository.findById(id);
	}
	
	public void deleteFileById(String id) throws Exception {
		Optional<MultimediaProperty> existeFile = multimediaPropertyRespository.findById(id);
		
		if(!existeFile.isPresent()) {
			throw new Exception("Archivo no encontrado");
		}
		
		multimediaPropertyRespository.delete(existeFile.get());
	}
}
