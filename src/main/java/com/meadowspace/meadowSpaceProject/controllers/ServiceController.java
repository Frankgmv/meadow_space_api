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
import com.meadowspace.meadowSpaceProject.data.DataService;
import com.meadowspace.meadowSpaceProject.entity.Services;
import com.meadowspace.meadowSpaceProject.services.ServicesService;

@Controller
@RequestMapping("/data")
public class ServiceController {
	ServicesService serviceService;

	public ServiceController(ServicesService serviceService) {
		this.serviceService = serviceService;
	}
	
	@GetMapping("/service")
	public ResponseEntity<List<Services>> listarServicios(){
		List<Services> servicios = serviceService.listarServicios();
		return new ResponseEntity<>(servicios, HttpStatus.OK);
	}
	
	// busquedas por el id de la propiedad
	@GetMapping("/service/{id}")
	public ResponseEntity<Services> buscarServicioByPropertyId(@PathVariable String id){
			Optional<Services> service = serviceService.buscarByProperty(id);

			if(service.isPresent()) {
				return new ResponseEntity<>(service.get(), HttpStatus.OK);				
			}else {
				return ResponseEntity.notFound().build();
			}

	}
	
	@PostMapping("/service")
	public ResponseEntity<String> crearService(@RequestBody DataService dataService) {
		try {
			serviceService.crearServicio(dataService);
			return new ResponseEntity<>("Servicio insertado", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/service/{id}")
	public ResponseEntity<String> updateService(@PathVariable String id, @RequestBody DataService dataService) {
		try {
			dataService.setId(id);
			serviceService.updateServicio(dataService);
			return new ResponseEntity<>("Servicio Actualizado", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/service/{id}")
	public ResponseEntity<String> deletePro(@PathVariable String id) {
		try {
			serviceService.eliminarServico(id);
			return new ResponseEntity<>("Servicio Eliminado", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
