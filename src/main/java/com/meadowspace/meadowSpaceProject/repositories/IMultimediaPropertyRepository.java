package com.meadowspace.meadowSpaceProject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.MultimediaProperty;


@Repository
public interface IMultimediaPropertyRepository extends JpaRepository<MultimediaProperty, String> {
	// Buscar Servicio de propiedad
		@Query("SELECT mul FROM MultimediaProperty mul WHERE mul.property.id= :id")
		public List<MultimediaProperty> findByPropertyId(@Param("id") String id);
}
