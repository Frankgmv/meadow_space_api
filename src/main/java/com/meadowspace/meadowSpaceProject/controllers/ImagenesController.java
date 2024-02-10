package com.meadowspace.meadowSpaceProject.controllers;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

@Controller
@RequestMapping("/resource")
public class ImagenesController {

	@GetMapping("/image/{nombre}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombre) throws IOException {
		Resource resource = (Resource) new PathResource("src/main/resources/static/pictures/" + nombre);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentLength(resource.contentLength()).body(resource);
    }
}
