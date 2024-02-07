package com.meadowspace.meadowSpaceProject.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private Double price;
	private String inictialDate;
	private String finishDate;
	private String descriptionEvent;
	
	// Ready
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	
	// Ready
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
