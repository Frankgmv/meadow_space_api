package com.meadowspace.meadowSpaceProject.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String cellphone;

    private String address;
    
    @Column(name = "picture", nullable = true)
    @Lob
    private byte[] picture;

    private String password;
    
    @Column(name = "rol", length = 20)
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "rol")
    private Role rol;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<CustomerOpinion> getCustomerOpinions() {
		return customerOpinions;
	}

	public void setCustomerOpinions(List<CustomerOpinion> customerOpinions) {
		this.customerOpinions = customerOpinions;
	}

	public List<MultimediaOpinions> getMultimediaOpinions() {
		return multimediaOpinions;
	}

	public void setMultimediaOpinions(List<MultimediaOpinions> multimediaOpinions) {
		this.multimediaOpinions = multimediaOpinions;
	}

	// Ready
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Property> properties;
    
    // Ready
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    
    // Ready
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CustomerOpinion> customerOpinions;
    
 // Ready
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MultimediaOpinions> multimediaOpinions;

}
