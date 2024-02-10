package com.meadowspace.meadowSpaceProject.data;

import org.springframework.web.multipart.MultipartFile;
import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.entity.User;

public class dataOpinionAndMult {
	private String comment;
	private Property property;
	private User user;
	private MultipartFile imagen;
	private String picture;
	
	
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Property getProperty() {
		return property;
	}


	public void setProperty(Property property) {
		this.property = property;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public MultipartFile getImagen() {
		return imagen;
	}


	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	@Override
	public String toString() {
		return "dataOpinionAndMult [comment=" + comment + ", property=" + property + ", user=" + user + ", imagen="
				+ imagen + ", picture=" + picture + "]";
	}
}
