package com.meadowspace.meadowSpaceProject.services;


import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.meadowspace.meadowSpaceProject.data.DataProperty;
import com.meadowspace.meadowSpaceProject.entity.Category;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.repositories.ICategoryRepository;
import com.meadowspace.meadowSpaceProject.repositories.IPropertyRepository;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;

@Service
public class PropertyService {

	IPropertyRepository propertyRepository;
	IUserRepository userRepository;
	ICategoryRepository categoryRepository;
	
	public PropertyService (IPropertyRepository propertyRepository,
			IUserRepository userRepository,
			ICategoryRepository categoryRepository) {
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public List<Property> listAllProperty(){
		return propertyRepository.findAll();
	}
	
	public Optional<Property> listPropertyById(String id){
		return propertyRepository.findById(id);
	}
	
	public List<Property> listAllPropertyByStatus(Boolean status){
		return propertyRepository.findByStatus(status);
	}
	
	public List<Property> listAllPropertyByUserId(Long id){
		return propertyRepository.findByUserId(id);
	}
	
	public List<Property> listAllPropertyByCategoryId(String id){
		return propertyRepository.findByCategoryId(id);
	}
	
 	public void crearProperty(DataProperty property) throws Exception {

		validar(property);
		
		List<Property> existeProperty = propertyRepository.findByNameAndUserId(property.getName(), property.getUser().getId());
		
		if(existeProperty.size() > 0) {
			throw new Exception("Ya tienes propiedades con ese nombre");
		}

		Optional<User> usuario = userRepository.findById(property.getUser().getId());
		Optional<Category> categoria = categoryRepository.findById(property.getCategory().getId());
		
		if(!usuario.isPresent()) {
			throw new Exception("Usuario no encontrado");
		}
		
		if(!categoria.isPresent()) {
			throw new Exception("Categoria no encontrada");
		}

		Property propiedad = new Property();

		propiedad.setLocation(property.getLocation());
		propiedad.setDescription(property.getDescription());
		propiedad.setStatus(property.getStatus());
		propiedad.setName(property.getName());
		propiedad.setType(property.getType());
		
		// verify data user and category 

		propiedad.setCategory(categoria.get());
		propiedad.setUser(usuario.get());

		propertyRepository.save(propiedad);
	}
 	
 	public void actulalizarProperty(DataProperty property) throws Exception {

		validar(property);
		
		Optional<Property> buscarProperty = propertyRepository.findById(property.getId());
		if(!buscarProperty.isPresent()) {
			throw new Exception("No se encontro la propiedad");
		}
		
		Optional<User> usuario = userRepository.findById(property.getUser().getId());
		if(!usuario.isPresent()) {
			throw new Exception("Usuario no encontrado");
		}
		
		Optional<Category> categoria = categoryRepository.findById(property.getCategory().getId());
		if(!categoria.isPresent()) {
			throw new Exception("Categoria no encontrada");
		}

		Property actualizarPropiedad = buscarProperty.get();

		actualizarPropiedad.setLocation(property.getLocation());
		actualizarPropiedad.setDescription(property.getDescription());
		actualizarPropiedad.setStatus(property.getStatus());
		actualizarPropiedad.setName(property.getName());
		actualizarPropiedad.setType(property.getType());

		// verify data user and category 
		actualizarPropiedad.setCategory(categoria.get());
		actualizarPropiedad.setUser(usuario.get());

		propertyRepository.save(actualizarPropiedad);
	}
 	
 	public void deleteProperty(String id) throws Exception {
		Optional<Property> propiedad = propertyRepository.findById(id);

		if (!propiedad.isPresent()) {
			throw new Exception("Propiedad con ID " + id + " no encontrado.");
		}
		propertyRepository.delete(propiedad.get());
	}

	private static void validar(DataProperty property) throws Exception {
		if (property.getLocation() == null) {
			throw new Exception(" Locacion de propiedad no puede ser nulo !!!");
		}

		if (property.getDescription() == null) {
			throw new Exception(" Descripcion de propiedad no puede ser vacío o nulo !!!");
		}

		if (property.getStatus() == null) {
			throw new Exception(" Estado de propiedad no puede ser nulo !!!");
		}

		if (property.getName() == null) {
			throw new Exception(" Nombre de propiedad no puede ser nulo !!!");
		}
		
		if (property.getType() == null) {
			throw new Exception(" Tipo de propiedad no puede ser nulo !!!");
		}
		
		if (property.getUser() == null) {
			throw new Exception("El usuario de la propiedad no puede ser nulo !!!");
		}
		
		if (property.getCategory() == null) {
			throw new Exception("La categoria de la propiedad no puede ser nulo !!!");
		}
	}
}
