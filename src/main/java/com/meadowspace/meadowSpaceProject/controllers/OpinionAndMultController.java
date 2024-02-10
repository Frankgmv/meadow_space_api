package com.meadowspace.meadowSpaceProject.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meadowspace.meadowSpaceProject.data.dataOpinionAndMult;
import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;
import com.meadowspace.meadowSpaceProject.services.OpinionAndMultService;
import com.meadowspace.meadowSpaceProject.services.impl.IUploadFilesService;

@Controller
@RequestMapping("/data")
public class OpinionAndMultController {

	IUploadFilesService uploadFileService;
	OpinionAndMultService opinionAndMultService;

	

	public OpinionAndMultController(IUploadFilesService uploadFileService,
			OpinionAndMultService opinionAndMultService) {
		this.uploadFileService = uploadFileService;
		this.opinionAndMultService = opinionAndMultService;
	}

	@PostMapping(value = "/opinion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> crearOpinion(@ModelAttribute dataOpinionAndMult dataRequest) {
		try {
			if (dataRequest.getImagen() != null && !dataRequest.getImagen().isEmpty()) {
				String uploadedFileUrl = uploadFileService.handleFileUpload(dataRequest.getImagen());
				dataRequest.setPicture(uploadedFileUrl);
				opinionAndMultService.crearCommentOpinion(dataRequest);
				opinionAndMultService.crearMultimediaOpinion(dataRequest);

				return new ResponseEntity<>("Comentario subido", HttpStatus.CREATED);
			}else {				
				opinionAndMultService.consultarOpiniones(dataRequest.getUser().getId(), dataRequest.getProperty().getId());
				opinionAndMultService.crearCommentOpinion(dataRequest);
  			return new ResponseEntity<>("Comentario subido", HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
