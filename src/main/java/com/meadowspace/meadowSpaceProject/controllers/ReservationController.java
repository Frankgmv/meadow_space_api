package com.meadowspace.meadowSpaceProject.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meadowspace.meadowSpaceProject.data.DataReservation;
import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.entity.Reservation;
import com.meadowspace.meadowSpaceProject.services.ReservationService;
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

@RestController
@RequestMapping("/data")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}


	@PostMapping(value = "/reservation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseFormat> crearReserva(@RequestBody DataReservation reserva){
		try {
			reservationService.crearReserva(reserva);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Reserva realizada exitosamente");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@GetMapping("/reservation")
	public ResponseEntity<ResponseFormat> AllReservas(){
		try {
			List<Reservation> reservas = reservationService.AllReserva();
			return ApiControllerUtil.buildResponse(reservas, HttpStatus.OK, true, "Lista de reservas");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@GetMapping("/reservation-property/{id}")
	public ResponseEntity<ResponseFormat> reservasPorPropiedad(@PathVariable String id){
		try {
			List<Reservation> reservas = reservationService.busacarReservaPorPropiedadId(id);
			return ApiControllerUtil.buildResponse(reservas, HttpStatus.OK, true, "Lista de reservas por propiedad");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@GetMapping("/reservation/{id}")
	public ResponseEntity<ResponseFormat> unaReserva(@PathVariable String id){
		try {
			Reservation reserva = reservationService.unaReserva(id);
			return ApiControllerUtil.buildResponse(reserva, HttpStatus.OK, true, "Lista de reservas por propiedad");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@PutMapping("/reservation/{id}")
	public ResponseEntity<ResponseFormat> modificarReservas(@PathVariable String id, @RequestBody DataReservation data){
		try {
			data.setId(id);
			reservationService.modificarReserva(data);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Reserva modificada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@DeleteMapping("/reservation/{id}")
	public ResponseEntity<ResponseFormat> eliminarReservas(@PathVariable String id){
		try {
			reservationService.deleteReserva(id);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Reserva eliminada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
}
