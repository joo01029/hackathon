package com.hackathon.handler;

import com.hackathon.domain.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Response> handleHttpClientException(HttpClientErrorException e){
		Response data = new Response(e.getStatusCode(), e.getMessage().substring(4));
		return new ResponseEntity<Response>(data, e.getStatusCode());
	}
	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<Response> handleHttpServerException(HttpServerErrorException e){
		Response data = new Response(e.getStatusCode(), e.getMessage().substring(4));
		return new ResponseEntity<Response>(data, e.getStatusCode());
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception e){
		e.printStackTrace();
		Response data = new Response(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러");
		return new ResponseEntity<Response>(data, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
