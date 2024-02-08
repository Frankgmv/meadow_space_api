package com.meadowspace.meadowSpaceProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Services;

@Repository
public interface IServiceRepository extends JpaRepository<Services, String> {

	// Buscar Servicio de propiedad
	@Query("SELECT ser FROM Services ser WHERE ser.property.id= :id")
	public Optional<Services> findByProperty(@Param("id") String id);
}
