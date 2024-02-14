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
import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.entity.Services;
import com.meadowspace.meadowSpaceProject.services.ServicesService;
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

@Controller
@RequestMapping("/data")
public class ServiceController {
	ServicesService serviceService;

	public ServiceController(ServicesService serviceService) {
		this.serviceService = serviceService;
	}
	
	@GetMapping("/service")
	public ResponseEntity<ResponseFormat> listarServicios(){
		List<Services> servicios = serviceService.listarServicios();
		return ApiControllerUtil.buildResponse(servicios, HttpStatus.OK, true, "Lista de servicios");
	}
	
	// busquedas por el id de la propiedad
	@GetMapping("/service/{id}")
	public ResponseEntity<ResponseFormat> buscarServicioByPropertyId(@PathVariable String id){
			Optional<Services> service = serviceService.buscarByProperty(id);

			if(service.isPresent()) {
				return ApiControllerUtil.buildResponse(service.get(), HttpStatus.OK, true, "List de servicios por propiedad");				
			}else {
				return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, "Servicio no encontrado");
			}

	}
	
	@PostMapping("/service")
	public ResponseEntity<ResponseFormat> crearService(@RequestBody DataService dataService) {
		try {
			serviceService.crearServicio(dataService);
			return ApiControllerUtil.buildResponse(null, HttpStatus.CREATED, true, "Servicio insertado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@PutMapping("/service/{id}")
	public ResponseEntity<ResponseFormat> updateService(@PathVariable String id, @RequestBody DataService dataService) {
		try {
			dataService.setId(id);
			serviceService.updateServicio(dataService);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Servicio actualizado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@DeleteMapping("/service/{id}")
	public ResponseEntity<ResponseFormat> deletePro(@PathVariable String id) {
		try {
			serviceService.eliminarServico(id);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Servicio eliminado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
}
