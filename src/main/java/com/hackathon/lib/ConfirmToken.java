package com.hackathon.lib;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RequiredArgsConstructor
@Component
public class ConfirmToken {

	public String removeStartString(HttpServletRequest request, String type) {
		try {
			Enumeration<String> token = request.getHeaders("Authorization");

			if (token.hasMoreElements()) {
				String value = token.nextElement();
				if (value.toLowerCase().startsWith(type.toLowerCase())) {
					return value.substring(type.length()).trim();
				}
				throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "잘못된 토큰 타입");
			}
			return null;
		} catch (HttpClientErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
		}
	}
}
