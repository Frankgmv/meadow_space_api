package com.meadowspace.meadowSpaceProject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meadowspace.meadowSpaceProject.data.ResponseFormat;

public class ApiControllerUtil {

	public static <T> ResponseEntity<ResponseFormat> buildResponse(T data, HttpStatus httpStatus, Boolean status,
			String message) {
		ResponseFormat response = new ResponseFormat();

		response.setData(data);
		response.setStatusCode(httpStatus.value());
		response.setStatus(status);
		response.setMessage(message);
		
		return new ResponseEntity<>(response, httpStatus);

	}
}
