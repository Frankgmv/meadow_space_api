package com.meadowspace.meadowSpaceProject.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFormat {
	
	private Boolean status;
	private String message;
	private Integer statusCode;
	private Object data;
}
