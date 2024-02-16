package com.meadowspace.meadowSpaceProject.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {

	@Query(value = "SELECT r FROM Reservation r " +
	        "WHERE r.property.id = :propertyId "+
	        "AND ((r.initialDate BETWEEN :initialDate AND :finishDate) " +
	        "OR (r.finishDate BETWEEN :initialDate AND :finishDate))")
	List<Reservation> buscarReservasExistentes(@Param("propertyId") String propertyId,
			@Param("initialDate") String initialDate,
			@Param("finishDate") String finishDate);
	
	@Query(value = "SELECT r FROM Reservation r " +
	        "WHERE r.property.id = :propertyId " +
	        "AND r.user.id != :idUser " +
	        "AND ((r.initialDate BETWEEN :initialDate AND :finishDate) " +
	        "OR (r.finishDate BETWEEN :initialDate AND :finishDate))",
	        nativeQuery = true)
	List<Reservation> buscarReservasExistentesAlModificar(@Param("propertyId") String propertyId,
	                                                  @Param("initialDate") String initialDate,
	                                                  @Param("finishDate") String finishDate,
	                                                  @Param("idUser") Long userId);

	
	@Query(value = "SELECT r FROM Reservation r WHERE r.property.id = :propertyId")
	List<Reservation> reservasByPropertyId(@Param("propertyId") String propertyId);
}
