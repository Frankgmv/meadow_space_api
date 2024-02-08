package com.meadowspace.meadowSpaceProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Property;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, String> {

	
	
}
