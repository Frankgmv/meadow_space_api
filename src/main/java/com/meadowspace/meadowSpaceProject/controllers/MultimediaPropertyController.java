package com.meadowspace.meadowSpaceProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.meadowspace.meadowSpaceProject.data.DataMultimediaProperty;
import com.meadowspace.meadowSpaceProject.entity.MultimediaProperty;
import com.meadowspace.meadowSpaceProject.entity.User;
import com.meadowspace.meadowSpaceProject.services.MultimediaPropertyService;
import com.meadowspace.meadowSpaceProject.services.img.UploadFilesService;

@Controller
@RequestMapping("/resource")
public class MultimediaPropertyController {

	private final UploadFilesService uploadFileService;
	private final MultimediaPropertyService multimediaPropertyService;

	public MultimediaPropertyController(UploadFilesService uploadFileService,
			MultimediaPropertyService multimediaPropertyService) {
		this.uploadFileService = uploadFileService;
		this.multimediaPropertyService = multimediaPropertyService;
	}

	@PostMapping(value = "/property", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadImageProperty(@ModelAttribute DataMultimediaProperty dataMulProperty) {
		try {
			String uploadedFileUrl = "";
			if (dataMulProperty.getImagen() != null && !dataMulProperty.getImagen().isEmpty()) {
				uploadedFileUrl = uploadFileService.handleFileUpload(dataMulProperty.getImagen());
				dataMulProperty.setPicture(uploadedFileUrl);
				multimediaPropertyService.crearArchivo(dataMulProperty);
				return new ResponseEntity<>("Imagen subida", HttpStatus.CREATED);
			} else {
				throw new Exception("Inserta la imagen");
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/property/{id}")
	public ResponseEntity<List<MultimediaProperty>> obtenerFilesByPropertyId(@PathVariable String id) {
		List<MultimediaProperty> files = multimediaPropertyService.obtenerFilesByPropertyId(id);
		return new ResponseEntity<>(files, HttpStatus.OK);
	}

	@DeleteMapping("/property/{id}")
	public ResponseEntity<String> eliminarById(@PathVariable String id) {
		try {
			Optional<MultimediaProperty> file = multimediaPropertyService.obtenerFileById(id);
			if (!file.isPresent()) {
				throw new Exception("Archivo no encontrado");
			}

			multimediaPropertyService.deleteFileById(id);
			
			if (file.get().getPicture() != null) {
				uploadFileService.deleteFile(file.get().getPicture());
			}
			return new ResponseEntity<>("Archivo eliminado", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
