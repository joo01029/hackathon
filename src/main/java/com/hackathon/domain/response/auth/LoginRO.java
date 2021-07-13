package com.hackathon.domain.response.auth;

import com.hackathon.domain.entity.School;
import com.hackathon.domain.response.school.GetSchoolRO;
import lombok.Data;

@Data
public class LoginRO {
	private String authToken;
	private GetSchoolRO school;

	public LoginRO(String token, School school) {
		authToken = token;
		this.school = new GetSchoolRO(school.getCode(), school.getName());
	}
}
