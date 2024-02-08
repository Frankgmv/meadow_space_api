package com.meadowspace.meadowSpaceProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, String> {

	@Query("SELECT cat FROM Category cat WHERE cat.category = :category")
	public Optional<Category> findByCategory(@Param("category") String category);

}
