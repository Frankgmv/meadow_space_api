package com.meadowspace.meadowSpaceProject.data;

import com.meadowspace.meadowSpaceProject.entity.Property;
import com.meadowspace.meadowSpaceProject.entity.User;

public class DataReservation {

	private String id;
	private double price;
	private String initialDate;
	private String finishDate;
	private String descriptionEvent;
	
	private Property property;
	
	private User user;
	
	@Override
	public String toString() {
		return "DataReservation [id=" + id + ", price=" + price + ", initialDate=" + initialDate + ", finishDate="
				+ finishDate + ", descriptionEvent=" + descriptionEvent + ", property=" + property + ", user=" + user
				+ "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getDescriptionEvent() {
		return descriptionEvent;
	}
	public void setDescriptionEvent(String descriptionEvent) {
		this.descriptionEvent = descriptionEvent;
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
	
	
	
}
