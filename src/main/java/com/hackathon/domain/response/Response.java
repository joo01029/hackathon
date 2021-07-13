package com.hackathon.domain.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
	private Integer status;
	private String message;

	public Response(HttpStatus status, String message){
		this.status = status.value();
		this.message = message.substring(3).trim();
	}
}
