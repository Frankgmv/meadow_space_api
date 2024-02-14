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
import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.services.PropertyService;
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

@Controller
@RequestMapping("/data")
public class PropertyController {

	private final PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@PostMapping("/property")
	public ResponseEntity<ResponseFormat> crearPropiedad(@RequestBody DataProperty dataProperty) {
		try {
			propertyService.crearProperty(dataProperty);
			return ApiControllerUtil.buildResponse(null, HttpStatus.CREATED, true, "Propiedad publicada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}

	// consultar por estatus status = true - false, 1 - 0
	@GetMapping("/property")
	public ResponseEntity<ResponseFormat> listarByStatus(@RequestParam(required = false) Boolean status) {
		if (status == null) {
			List<Property> propiedades = propertyService.listAllProperty();
			return ApiControllerUtil.buildResponse(propiedades, HttpStatus.OK, true, "Propiedades");
		} else {
			List<Property> propiedades = propertyService.listAllPropertyByStatus(status);
			return ApiControllerUtil.buildResponse(propiedades, HttpStatus.OK, true, "Lista de propiedades por estado");
		}
	}

	// Consultar por id propiedad
	@GetMapping("/property/{id}")
	public ResponseEntity<ResponseFormat> listarById(@PathVariable String id) {
		Optional<Property> propiedad = propertyService.listPropertyById(id);
		if (propiedad.isPresent()) {
			return ApiControllerUtil.buildResponse(propiedad, HttpStatus.OK, true, "Propiedad obtenida");
		} else {
			return ApiControllerUtil.buildResponse(null, HttpStatus.NOT_FOUND, false, "Propiedad no encontrada");
		}
	}

	// List by User Id
	@GetMapping("/property-user/{id}")
	public ResponseEntity<ResponseFormat> listarByUserId(@PathVariable Long id) {
		List<Property> propiedades = propertyService.listAllPropertyByUserId(id);
		return ApiControllerUtil.buildResponse(propiedades, HttpStatus.OK, true, "Lista de propiedades por usuario");
	}

	//	List by Category Id
	@GetMapping("/property-category/{id}")
	public ResponseEntity<ResponseFormat> listarByCategoryId(@PathVariable String id) {
		List<Property> propiedades = propertyService.listAllPropertyByCategoryId(id);
		return ApiControllerUtil.buildResponse(propiedades, HttpStatus.OK, true, "Lista de propiedades por categoria");
	}
	
	@PutMapping("/property/{id}")
	public ResponseEntity<ResponseFormat> crearPropiedad(@PathVariable String id, @RequestBody DataProperty dataProperty) {
		try {
			dataProperty.setId(id);
			propertyService.actulalizarProperty(dataProperty);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Propiedad actualizada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	

	@DeleteMapping("/property/{id}")
	public ResponseEntity<ResponseFormat> deleteById(@PathVariable String id) {
		try {
			propertyService.deleteProperty(id);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Propiedad eliminada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
}
