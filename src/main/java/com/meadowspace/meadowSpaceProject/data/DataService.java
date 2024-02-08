package com.meadowspace.meadowSpaceProject.data;

import java.util.Map;

import com.meadowspace.meadowSpaceProject.entity.Property;

public class DataService {
	private String id;
	private Map<String, Object> service;
	private Property property;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, Object> getService() {
		return service;
	}
	
	public void setService(Map<String, Object> service) {
		this.service = service;
	}
	
	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return "DataService [id=" + id + ", service=" + service + ", property=" + property + "]";
	}
		
}
