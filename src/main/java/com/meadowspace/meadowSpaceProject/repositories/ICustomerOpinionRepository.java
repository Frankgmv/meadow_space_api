package com.meadowspace.meadowSpaceProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;

@Repository
public interface ICustomerOpinionRepository extends JpaRepository<CustomerOpinion, String> {
	// Buscar propiedades por name y id propietario
		@Query("SELECT op FROM CustomerOpinion op WHERE op.property.id= :propertyId")
		public List<CustomerOpinion> consultarComentario(@Param("propertyId") String propertyId);
}
