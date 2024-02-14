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
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

@Controller
@RequestMapping("/data")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/category")
	public ResponseEntity<ResponseFormat> obtenerCategoria() {
		List<Category> listCategory = categoryService.listarCategory();
		return ApiControllerUtil.buildResponse(listCategory, HttpStatus.OK, true, "Lista de categorias");
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<ResponseFormat> obtenerUsuarioById(@PathVariable String id) {
		Optional<Category> categoria = categoryService.obtenerCategoryPorId(id);
		if (categoria.isPresent()) {
			return ApiControllerUtil.buildResponse(categoria.get(), HttpStatus.OK, true, "categoria obtenida");
		} else {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, "categoria no encontrada");
		}
	}
	
	@PostMapping("/category")
	public ResponseEntity<ResponseFormat> createCategory(@RequestBody DataCategory dataCategory) {
	  try {
	    categoryService.crearCategory(dataCategory.getCategory());
	    return ApiControllerUtil.buildResponse(null, HttpStatus.CREATED, true, "Categoria publicada");
	  } catch (Exception e) {
	    return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
	  }
	}
	
	@PutMapping("/category/{id}")
	public ResponseEntity<ResponseFormat> updateCategory(@PathVariable String id, @RequestBody DataCategory dataCategory) throws Exception {
		try {
			categoryService.updateCategory(id, dataCategory.getCategory());
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Categoria actualizada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<ResponseFormat> eliminarCategoria(@PathVariable String id) {
		try {
			categoryService.deleteCategory(id);
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Categoria eliminada");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
}
