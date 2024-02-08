package com.meadowspace.meadowSpaceProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meadowspace.meadowSpaceProject.entity.Category;
import com.meadowspace.meadowSpaceProject.repositories.ICategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private final ICategoryRepository categoryRepository;

	public CategoryService(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	
	@Transactional
	public void crearCategory(String category) throws Exception {

		if(category == null) {
			throw new Exception("La categoria no puede ser nula!!!");
		}

		Optional<Category> existingCategory = categoryRepository.findByCategory(category);

		if (existingCategory.isPresent()) {
			throw new Exception("Categoria " + category + " ya existe en la base de datos.");
		}

		Category categoria = new Category();

		categoria.setCategory(category);

		categoryRepository.save(categoria);
	}

	public List<Category> listarCategory() {
		return categoryRepository.findAll();
	}

	public Optional<Category> obtenerCategoryPorId(String id) {
		return categoryRepository.findById(id);
	}

	@Transactional
	public Category updateCategory(String id, String category) throws Exception {
		Optional<Category> categoria = categoryRepository.findById(id);
		if (!categoria.isPresent()) {
			throw new Exception("Categoria con ID " + id + " no encontrada.");
		}

		Category updatedCategory = categoria.get();
		updatedCategory.setCategory(category);

		return categoryRepository.save(updatedCategory);
	}

	public void deleteCategory(String id) throws Exception {
		Optional<Category> categoria = categoryRepository.findById(id);

		if (!categoria.isPresent()) {
			throw new Exception("Categoria con ID " + id + " no encontrado.");
		}
		categoryRepository.delete(categoria.get());
	}

}
