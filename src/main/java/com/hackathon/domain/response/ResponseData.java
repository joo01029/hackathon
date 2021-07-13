package com.hackathon.domain.response;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseData<t> extends Response{
	private t data;
	public ResponseData(HttpStatus status, String message, t data) {
		super(status, message);
		this.data = data;
	}
}
