package com.meadowspace.meadowSpaceProject.data;


import com.meadowspace.meadowSpaceProject.entity.Role;

public class DataUser {
	private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String cellphone;

    private String address;
    
    private String picture;

    private String password;
    
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
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

	@Override
	public String toString() {
		return "DataUser [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
				+ phone + ", cellphone=" + cellphone + ", address=" + address + ", picture=" +picture
				+ ", password=" + password + ", rol=" + rol + "]";
	}
}
