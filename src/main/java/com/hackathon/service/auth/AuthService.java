package com.hackathon.service.auth;

import com.hackathon.domain.dto.auth.LoginDto;
import com.hackathon.domain.dto.auth.SigninDto;
import com.hackathon.domain.response.auth.LoginRO;

public interface AuthService {
	void signup(SigninDto signinDto);

	LoginRO login(LoginDto loginDto);

	void checkExist(String id);
}
