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
