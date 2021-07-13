package com.hackathon.service.jwt;

import com.hackathon.domain.entity.User;


public interface JwtService {
	User accessTokenDecoding(String token);

	String createToken(String subject, long accessTokenTime);
}
