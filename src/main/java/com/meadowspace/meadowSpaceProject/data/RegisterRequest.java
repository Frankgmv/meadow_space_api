package com.meadowspace.meadowSpaceProject.data;

import org.springframework.web.multipart.MultipartFile;

import com.meadowspace.meadowSpaceProject.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String cellphone;

    private String address;
    
    private MultipartFile imagen;
    
    private String picture;

    private String password;
    
    private Role rol;
}
