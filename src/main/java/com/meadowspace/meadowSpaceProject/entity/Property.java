package com.meadowspace.meadowSpaceProject.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Property {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name;
	private Boolean status;
	private String description;
	private String type;
	private String location;
	
	// Ready
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User user;
	
	// Ready
	@ManyToOne
	private Category category;
	
	// Ready
	@OneToMany(mappedBy = "property")
    private List<Reservation> reservations;
	
	// Ready
	@OneToMany(mappedBy = "property")
	private List<CustomerOpinion> customerOpinions;
	
	// Ready
	@OneToMany(mappedBy = "property")
	private List<Services> services;
	
	// Ready
	@OneToMany(mappedBy = "property")
	private List<MultimediaProperty> multimediaProperties;
	
	@OneToMany(mappedBy = "property")
	private List<MultimediaOpinions> multimediaOpinions;
}
