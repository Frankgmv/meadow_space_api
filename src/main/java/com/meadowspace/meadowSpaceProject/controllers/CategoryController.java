package com.meadowspace.meadowSpaceProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meadowspace.meadowSpaceProject.data.DataCategory;
import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.entity.Category;
import com.meadowspace.meadowSpaceProject.services.CategoryService;

@Controller
@RequestMapping("/data")
public class CategoryController {

	@Autowired
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/category")
	public ResponseEntity<ResponseFormat> obtenerCategoria() {
		List<Category> listCategory = categoryService.listarCategory();
		ResponseFormat response = new ResponseFormat();
		response.setData(listCategory);
		response.setMessage("Lista de categorias");
		response.setStatus(true);
		response.setStatusCode(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<Category> obtenerUsuarioById(@PathVariable String id) {
		Optional<Category> categoria = categoryService.obtenerCategoryPorId(id);
		if (categoria.isPresent()) {
			return ResponseEntity.ok(categoria.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/category")
	public ResponseEntity<String> createCategory(@RequestBody DataCategory dataCategory) {
	  try {
	    
	    categoryService.crearCategory(dataCategory.getCategory());
	    return new ResponseEntity<>("categoria creada", HttpStatus.CREATED);
	  } catch (Exception e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }
	}
	
	@PutMapping("/category/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable String id, @RequestBody DataCategory dataCategory) throws Exception {
		try {
			categoryService.updateCategory(id, dataCategory.getCategory());
			return new ResponseEntity<>("Categoria actualizado", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<String> eliminarCategoria(@PathVariable String id) {
		try {
			categoryService.deleteCategory(id);
			return new ResponseEntity<>("Categoria eliminada", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
