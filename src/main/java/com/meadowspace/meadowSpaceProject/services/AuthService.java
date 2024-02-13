package com.meadowspace.meadowSpaceProject.services;

import com.meadowspace.meadowSpaceProject.data.AuthResponse;
import com.meadowspace.meadowSpaceProject.data.AuthenticateRequest;
import com.meadowspace.meadowSpaceProject.data.RegisterRequest;

public interface AuthService {
	AuthResponse register(RegisterRequest request) throws Exception;

	AuthResponse authenticate(AuthenticateRequest request)throws Exception;
	
}
