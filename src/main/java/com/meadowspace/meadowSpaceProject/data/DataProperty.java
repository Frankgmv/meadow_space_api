package com.meadowspace.meadowSpaceProject.data;

import com.meadowspace.meadowSpaceProject.entity.Category;
import com.meadowspace.meadowSpaceProject.entity.User;

public class DataProperty {
	private String id;
	private String name;
	private Boolean status;
	private String description;
	private String type;
	private String location;
	
	private User user;
	
	private Category category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "DataProperty [id=" + id + ", name=" + name + ", status=" + status + ", description=" + description
				+ ", type=" + type + ", location=" + location + ", user=" + user + ", category=" + category + "]";
	}
}
