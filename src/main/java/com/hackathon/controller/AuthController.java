package com.hackathon.controller;

import com.hackathon.domain.dto.auth.LoginDto;
import com.hackathon.domain.dto.auth.SigninDto;
import com.hackathon.domain.response.Response;
import com.hackathon.domain.response.ResponseData;
import com.hackathon.domain.response.auth.LoginRO;
import com.hackathon.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	@ApiOperation("회원가입")
	public Response signup(@RequestBody SigninDto signinDto) {
		authService.signup(signinDto);

		return new Response(HttpStatus.OK, "성공");
	}

	@PostMapping("/login")
	@ApiOperation("로그인")
	public ResponseData<LoginRO> login(@RequestBody LoginDto loginDto) {
		LoginRO jwt = authService.login(loginDto);

		return new ResponseData<>(HttpStatus.OK, "성공", jwt);
	}

	@GetMapping("/exist")
	@ApiOperation("아이디 존재여부")
	public Response checkExist(@RequestParam String id) {
		authService.checkExist(id);

		return new Response(HttpStatus.OK, "성공");
	}
}
