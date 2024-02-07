package com.meadowspace.meadowSpaceProject.services.img;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.meadowspace.meadowSpaceProject.services.impl.IUploadFilesService;

@Service
public class UploadFilesService implements IUploadFilesService {
	@Override
	public String handleFileUpload(MultipartFile file) throws Exception {
		try {
			String fileName = UUID.randomUUID().toString();
			byte[] bytes = file.getBytes();
			String fileOriginaName = file.getOriginalFilename();
			
			Long fileSize = file.getSize();
			Long maxfileSize = (long) (6 * 1024* 1024);
			
			if (fileSize > maxfileSize) {
				// return "Archivo no puede ser mayor a 6MB";
				throw new Exception("Archivo no puede ser mayor a 6MB");
			}
			
			// Type of file
			if (!fileOriginaName.endsWith(".jpg") &&
				!fileOriginaName.endsWith(".jpeg") &&
				!fileOriginaName.endsWith(".png")) {
				// return "Solo recibe formato .jpg .jpeg .png";
				throw new Exception("Solo recibe formato .jpg .jpeg .png");
			}
			
			String fileExtention = fileOriginaName.substring(fileOriginaName.lastIndexOf("."));
			String newFileName = fileName + fileExtention;
			
			File folder = new File("src/main/resources/static/pictures");

			if(!folder.exists()) {
				folder.mkdir();
			}
			
			Path path = Paths.get("src/main/resources/static/pictures/" + newFileName);
			
			Files.write(path, bytes);
			
			String pathName = path.toString(); 
			String dividir = pathName.substring(pathName.lastIndexOf("\\"));
			String result = dividir.substring(1);
			return result;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
