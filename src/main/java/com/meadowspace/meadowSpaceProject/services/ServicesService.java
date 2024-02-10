package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meadowspace.meadowSpaceProject.data.DataService;
import com.meadowspace.meadowSpaceProject.entity.Services;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.repositories.IPropertyRepository;
import com.meadowspace.meadowSpaceProject.repositories.IServiceRepository;

@Service
public class ServicesService {
	IServiceRepository serviceRepository;
	IPropertyRepository propertyRepository;
	
	public ServicesService (IServiceRepository serviceRepository,
			IPropertyRepository propertyRepository) {
		this.serviceRepository = serviceRepository;
		this.propertyRepository = propertyRepository;
	}
	
	public List<Services> listarServicios(){
		return serviceRepository.findAll();
	}
	
	public Optional<Services> buscarByProperty(String id){
		return serviceRepository.findByPropertyId(id);
	}
	
	public void crearServicio(DataService service) throws Exception {
		validar(service);
		
		Optional<Services> existeServicio = serviceRepository.findByPropertyId(service.getProperty().getId());
		if(existeServicio.isPresent()) {
			throw new Exception("El servicio ya esta insertado!!");
		}
		
		Optional<Property> existePropiedad = propertyRepository.findById(service.getProperty().getId());
		if(!existePropiedad.isPresent()) {
			throw new Exception("Propiedad no encontrada!!");
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(service.getService());
		
		Services servicio = new Services();

		servicio.setService(json.toString());
		servicio.setProperty(existePropiedad.get());

		serviceRepository.save(servicio);
	}
	
	public void updateServicio(DataService service) throws Exception {
		validar(service);
		
		Optional<Services> existeServicio = serviceRepository.findById(service.getId());
		if(!existeServicio.isPresent()) {
			throw new Exception("Servicio no encontrado!!");
		}
		
		Optional<Property> existePropiedad = propertyRepository.findById(service.getProperty().getId());
		if(!existePropiedad.isPresent()) {
			throw new Exception("Propiedad no encontrada!!");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(service.getService());
		
		Services servicio = existeServicio.get();

		servicio.setService(json.toString());
		servicio.setProperty(existePropiedad.get());

		serviceRepository.save(servicio);
	}
	
	public void eliminarServico(String id) throws Exception {
		Optional<Services> existeServicio = serviceRepository.findById(id);
		if(!existeServicio.isPresent()) {
			throw new Exception("El servicio con ID "+id+" no encontrado!!");
		}
		
		serviceRepository.delete(existeServicio.get());
	}
	
	private static void validar(DataService service) throws Exception {
		if (service.getService() == null) {
			throw new Exception("servicio no puede ser nulo !!!");
		}
		
		if (service.getProperty() == null) {
			throw new Exception("La Propiedad del servicio no puede ser nulo !!!");
		}
	}
}
