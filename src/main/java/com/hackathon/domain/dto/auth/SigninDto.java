package com.hackathon.domain.dto.auth;

import lombok.Data;

@Data
public class SigninDto {
	private String id;
	private String password;
	private String name;
	private Byte grade;
	private Byte classNum;
	private String schoolCode;
	private String schoolName;
}
