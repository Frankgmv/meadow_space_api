package com.meadowspace.meadowSpaceProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meadowspace.meadowSpaceProject.data.DataProperty;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.services.PropertyService;

@Controller
@RequestMapping("/data")
public class PropertyController {

	private final PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@PostMapping("/property")
	public ResponseEntity<String> crearPropiedad(@RequestBody DataProperty dataProperty) {
		try {
			propertyService.crearProperty(dataProperty);
			return new ResponseEntity<>("Propiedad registrada", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// consultar por estatus status = true - false, 1 - 0
	@GetMapping("/property")
	public ResponseEntity<List<Property>> listarByStatus(@RequestParam(required = false) Boolean status) {
		if (status == null) {
			List<Property> propiedades = propertyService.listAllProperty();
			return new ResponseEntity<>(propiedades, HttpStatus.OK);
		} else {
			List<Property> propiedades = propertyService.listAllPropertyByStatus(status);
			return new ResponseEntity<>(propiedades, HttpStatus.OK);
		}
	}

	// Consultar por id propiedad
	@GetMapping("/property/{id}")
	public ResponseEntity<Property> listarById(@PathVariable String id) {
		Optional<Property> propiedad = propertyService.listPropertyById(id);
		if (propiedad.isPresent()) {
			return ResponseEntity.ok(propiedad.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// List by User Id
	@GetMapping("/property-user/{id}")
	public ResponseEntity<List<Property>> listarByUserId(@PathVariable Long id) {
		List<Property> propiedadades = propertyService.listAllPropertyByUserId(id);
		return ResponseEntity.ok(propiedadades);
	}

	//	List by Category Id
	@GetMapping("/property-category/{id}")
	public ResponseEntity<List<Property>> listarByCategoryId(@PathVariable String id) {
		List<Property> propiedadades = propertyService.listAllPropertyByCategoryId(id);
		return ResponseEntity.ok(propiedadades);
	}
	
	@PutMapping("/property/{id}")
	public ResponseEntity<String> crearPropiedad(@PathVariable String id, @RequestBody DataProperty dataProperty) {
		try {
			dataProperty.setId(id);
			propertyService.actulalizarProperty(dataProperty);
			return new ResponseEntity<>("Propiedad Actualizada", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

	@DeleteMapping("/property/{id}")
	public ResponseEntity<String> deleteById(@PathVariable String id) {
		try {
			propertyService.deleteProperty(id);
			return new ResponseEntity<>("Propiedad eliminada", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
