package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.data.DataReservation;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.entity.Reservation;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.repositories.IPropertyRepository;
import com.meadowspace.meadowSpaceProject.repositories.IReservationRepository;
import com.meadowspace.meadowSpaceProject.repositories.IUserRepository;

@Service
public class ReservationService {

	private final IReservationRepository reservationRepository;
	private final IUserRepository userRepository;
	private final IPropertyRepository propertyRepository;

	public ReservationService(IReservationRepository reservationRepository, IUserRepository userRepository,
			IPropertyRepository propertyRepository) {
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
	}

	public void crearReserva(DataReservation data) throws Exception {
		validarReserva(data);
		
		Optional<User> existUser = userRepository.findById(data.getUser().getId());
		Optional<Property> existProperty = propertyRepository.findById(data.getProperty().getId());
		List<Reservation> reservasHechas = reservationRepository.buscarReservasExistentes(data.getProperty().getId(), data.getInitialDate(), data.getFinishDate());

		if (!reservasHechas.isEmpty()) {
			throw new Exception("Ya existen reservas en esta fecha");
		} 

		if(existUser.isEmpty()) {
			throw new Exception("Usuario no encontrado");
		}
		
		if(existProperty.isEmpty()) {
			throw new Exception("Propiedad no encontrada");
		}
		
		
		Reservation reserva = new Reservation();
		reserva.setDescriptionEvent(data.getDescriptionEvent());
		reserva.setFinishDate(data.getFinishDate());
		reserva.setInitialDate(data.getInitialDate());
		reserva.setPrice(data.getPrice());
		reserva.setUser(existUser.get());
		reserva.setProperty(existProperty.get());
		
		reservationRepository.save(reserva);
	}
	
	public void modificarReserva(DataReservation data) throws Exception {
		validarReserva(data);
		Optional<Reservation> encontrarReserva = reservationRepository.findById(data.getId());
		
		if(encontrarReserva.isEmpty()) {
			throw new Exception("Reserva no encontrado");
		}
		
		Optional<User> existUser = userRepository.findById(data.getUser().getId());
		Optional<Property> existProperty = propertyRepository.findById(data.getProperty().getId());
//		List<Reservation> reservasHechas = reservationRepository.buscarReservasExistentesAlModificar(data.getProperty().getId(), data.getInitialDate(), data.getFinishDate(), data.getUser().getId());
//		
//		if (!reservasHechas.isEmpty()) {
//			throw new Exception("Ya existen reservas en esta fecha");
//		} 

		if(existUser.isEmpty()) {
			throw new Exception("Usuario no encontrado");
		}
		
		if(existProperty.isEmpty()) {
			throw new Exception("Propiedad no encontrada");
		}
		
		Reservation reserva = encontrarReserva.get();

		reserva.setDescriptionEvent(data.getDescriptionEvent());
		reserva.setFinishDate(data.getFinishDate());
		reserva.setInitialDate(data.getInitialDate());
		reserva.setPrice(data.getPrice());
		reserva.setUser(existUser.get());
		reserva.setProperty(existProperty.get());
		
		reservationRepository.save(reserva);
	}
	
	public List<Reservation> AllReserva(){
		return reservationRepository.findAll();
		
	}
	
	public Reservation unaReserva(String id) throws Exception {
		Optional<Reservation> reserva =  reservationRepository.findById(id);
		
		if(reserva.isEmpty()) {
			throw new Exception("Reserva no encontrada");
		}
		
		return reserva.get();
	}
	
	public List<Reservation> busacarReservaPorPropiedadId(String propiedadId) throws Exception {
		
		Optional<Property> existProperty = propertyRepository.findById(propiedadId); 
		if(existProperty.isEmpty()) {
			throw new Exception("Propiedad no encontrada");
		}
		
		return reservationRepository.reservasByPropertyId(propiedadId);
	}
	
	public void deleteReserva(String id) throws Exception {
		Optional<Reservation> encontrarReserva = reservationRepository.findById(id);
		
		if(encontrarReserva.isEmpty()) {
			throw new Exception("Reserva no encontrada");
		}

		reservationRepository.delete(encontrarReserva.get());
	}
	
	public void validarReserva(DataReservation reserva) throws Exception {
	    if (reserva.getPrice() <= 0) {
	        throw new Exception("El precio debe ser mayor a cero.");
	    }
	    if (isEmpty(reserva.getInitialDate()) || isEmpty(reserva.getFinishDate())) {
	        throw new Exception("Las fechas inicial y final son obligatorias.");
	    }
	    if (isEmpty(reserva.getDescriptionEvent())) {
	        throw new Exception("La descripción del evento es obligatoria.");
	    }
	    if (reserva.getProperty() == null) {
	        throw new Exception("La propiedad es obligatoria.");
	    }
	    if (reserva.getUser() == null) {
	        throw new Exception("El usuario es obligatorio.");
	    }

	    if (!isValidDate(reserva.getInitialDate())) {
	        throw new Exception("Formato de fecha inicial inválido.");
	    }
	    if (!isValidDate(reserva.getFinishDate())) {
	        throw new Exception("Formato de fecha final inválido.");
	    }
	}

	private boolean isEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

	public static boolean isValidDate(String date) {
	    String regex = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$";

	    if (!Pattern.matches(regex, date)) {
	        return false;
	    }

	    int year = Integer.parseInt(date.substring(0, 4));
	    int month = Integer.parseInt(date.substring(5, 7));
	    int day = Integer.parseInt(date.substring(8, 10));
	    int hour = Integer.parseInt(date.substring(11, 13));
	    int minute = Integer.parseInt(date.substring(14, 16));

	    if (year < 2024 || year > 2028) {
	        return false;
	    }
	    if (month < 1 || month > 12) {
	        return false;
	    }
	    if (day < 1 || day > 31) {
	        return false;
	    }
	    if (month == 4 || month == 6 || month == 9 || month == 11) {
	        if (day > 30) {
	            return false;
	        }
	    }

	    if (month == 2) {
	        if (isLeapYear(year)) {
	            if (day > 29) {
	                return false;
	            }
	        } else {
	            if (day > 28) {
	                return false;
	            }
	        }
	    }

	    if (hour < 0 || hour > 23) {
	        return false;
	    }
	    if (minute < 0 || minute > 59) {
	        return false;
	    }

	    return true;
	}

	private static boolean isLeapYear(int year) {
	    return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	}

}
