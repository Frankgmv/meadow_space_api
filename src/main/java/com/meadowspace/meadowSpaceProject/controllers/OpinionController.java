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

import com.meadowspace.meadowSpaceProject.data.dataOpinionAndMult;
import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;
import com.meadowspace.meadowSpaceProject.services.OpinionService;
import com.meadowspace.meadowSpaceProject.services.img.UploadFilesService;

@Controller
@RequestMapping("/data")
public class OpinionController {

	private final UploadFilesService uploadFileService;
	OpinionService opinionService;

	public OpinionController(UploadFilesService uploadFileService,
			OpinionService opinionAndMultService) {
		this.uploadFileService = uploadFileService;
		this.opinionService = opinionAndMultService;
	}

	@PostMapping(value = "/opinion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> crearOpinion(@ModelAttribute dataOpinionAndMult dataRequest) {
		try {
			String uploadedFileUrl = "";
			if (dataRequest.getImagen() != null && !dataRequest.getImagen().isEmpty()) {
				uploadedFileUrl = uploadFileService.handleFileUpload(dataRequest.getImagen());
				dataRequest.setPicture(uploadedFileUrl);
			}

			opinionService.crearCommentOpinion(dataRequest);
			return new ResponseEntity<>("Comentario subido", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/opinion/{id}")
	public ResponseEntity<List<CustomerOpinion>> obtenerOpinions(@PathVariable String id){
		List<CustomerOpinion> opiniones = opinionService.consultarOpiniones(id);
		return new ResponseEntity<>(opiniones, HttpStatus.OK);
	}
	
	@DeleteMapping("/opinion/{id}")
	public ResponseEntity<String> eliminarOpinion(@PathVariable String id){
		try {
			Optional<CustomerOpinion> opinion = opinionService.obtenerById(id);
			if (!opinion.isPresent()) {
				throw new Exception("Archivo no encontrado");
			}

			opinionService.deleteCommentById(id);

			if (opinion.get().getPicture() != null) {
				uploadFileService.deleteFile(opinion.get().getPicture());
			}
			return new ResponseEntity<>("Comentario Eliminado", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
