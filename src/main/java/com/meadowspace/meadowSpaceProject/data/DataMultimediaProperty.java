package com.meadowspace.meadowSpaceProject.data;

import org.springframework.web.multipart.MultipartFile;

import com.meadowspace.meadowSpaceProject.entity.Property;

public class DataMultimediaProperty {

	private String id;
	
	private String picture;

	private MultipartFile imagen;
	
	private Property property;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}

	public MultipartFile getImagen() {
		return imagen;
	}


	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}


	public Property getProperty() {
		return property;
	}


	public void setProperty(Property property) {
		this.property = property;
	}


	@Override
	public String toString() {
		return "DataMultimediaProperty [id=" + id + ", imagen=" + imagen + ", property=" + property + "]";
	}
}
