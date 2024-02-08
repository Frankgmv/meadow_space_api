package com.meadowspace.meadowSpaceProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Property;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, String> {

	// Buscar propiedades activas
	@Query("SELECT prop FROM Property prop WHERE prop.status = :status")
	public List<Property> findByStatus(@Param("status") Boolean status);

	// Buscar propiedades por propietario
	@Query("SELECT prop FROM Property prop WHERE prop.user.id = :id")
	public List<Property> findByUserId(@Param("id") Long id);

	// Buscar propiedades por Categorias
	@Query("SELECT prop FROM Property prop WHERE prop.category.id = :id")
	public List<Property> findByCategoryId(@Param("id") String id);
	
	// Buscar propiedades por name y id propietario
	@Query("SELECT prop FROM Property prop WHERE prop.name= :name and prop.user.id =:id")
	public List<Property> findByNameAndUserId(@Param("name") String name, @Param("id") Long id);
}
