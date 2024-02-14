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

import com.meadowspace.meadowSpaceProject.data.ResponseFormat;
import com.meadowspace.meadowSpaceProject.data.dataOpinionAndMult;
import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;
import com.meadowspace.meadowSpaceProject.services.OpinionService;
import com.meadowspace.meadowSpaceProject.services.img.UploadFilesService;
import com.meadowspace.meadowSpaceProject.utils.ApiControllerUtil;

@Controller
@RequestMapping("/data")
public class OpinionController {

	private final UploadFilesService uploadFileService;
	OpinionService opinionService;

	public OpinionController(UploadFilesService uploadFileService, OpinionService opinionAndMultService) {
		this.uploadFileService = uploadFileService;
		this.opinionService = opinionAndMultService;
	}

	@PostMapping(value = "/opinion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseFormat> crearOpinion(@ModelAttribute dataOpinionAndMult dataRequest) {
		try {
			String uploadedFileUrl = "";
			if (dataRequest.getImagen() != null && !dataRequest.getImagen().isEmpty()) {
				uploadedFileUrl = uploadFileService.handleFileUpload(dataRequest.getImagen());
				dataRequest.setPicture(uploadedFileUrl);
			}

			opinionService.crearCommentOpinion(dataRequest);
			
			return ApiControllerUtil.buildResponse(null, HttpStatus.CREATED, true, "Comentario subido");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}

	@GetMapping("/opinion/{id}")
	public ResponseEntity<ResponseFormat> obtenerOpinionById(@PathVariable String id) {
			Optional<CustomerOpinion> opinion = opinionService.obtenerById(id);
			if(opinion.isEmpty()) {
				return ApiControllerUtil.buildResponse(null, HttpStatus.NO_CONTENT, false, "Comentario no encontrado");
			}
			return ApiControllerUtil.buildResponse(opinion.get(), HttpStatus.OK, true, "Comentario obtenido");
	}

	@GetMapping("/opinion-property/{id}")
	public ResponseEntity<ResponseFormat> obtenerOpinions(@PathVariable String id) {
		List<CustomerOpinion> opiniones = opinionService.consultarOpiniones(id);
		
		return ApiControllerUtil.buildResponse(opiniones, HttpStatus.OK, true, "Lista de comentarios por propiedad");
	}

	@GetMapping("/opinion")
	public ResponseEntity<ResponseFormat> obtenerTodasLasOpinions() {
		List<CustomerOpinion> opiniones = opinionService.consultarTodasLasOpiniones();
		
		return ApiControllerUtil.buildResponse(opiniones, HttpStatus.OK, true, "Lista de comentarios");
	}

	@DeleteMapping("/opinion/{id}")
	public ResponseEntity<ResponseFormat> eliminarOpinion(@PathVariable String id) {
		try {
			Optional<CustomerOpinion> opinion = opinionService.obtenerById(id);
			if (!opinion.isPresent()) {
				throw new Exception("Archivo no encontrado");
			}

			opinionService.deleteCommentById(id);

			if (opinion.get().getPicture() != null) {
				uploadFileService.deleteFile(opinion.get().getPicture());
			}
			return ApiControllerUtil.buildResponse(null, HttpStatus.OK, true, "Comentario eliminado");
		} catch (Exception e) {
			return ApiControllerUtil.buildResponse(null, HttpStatus.BAD_REQUEST, false, e.getMessage());
		}
	}
}
