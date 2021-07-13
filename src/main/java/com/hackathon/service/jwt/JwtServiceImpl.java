package com.hackathon.service.jwt;

import com.hackathon.domain.entity.User;
import com.hackathon.domain.repo.UserRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

	private final UserRepo userRepository;

	@Value("${auth.access}")
	String ACCESSSECRET_KEY;

	@Override
	@Transactional(readOnly = true)
	public String createToken(String subject, long ttlMillis) {
		if (ttlMillis <= 0)
			throw new RuntimeException("Expiry time must be greater than Zero : [" + ttlMillis + "] ");

		try {
			Key signingKey = makeSigningKey();
			return encodingToken(subject, signingKey, ttlMillis);
		} catch (HttpServerErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
		}
	}

	private Key makeSigningKey() {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] secretKey;
		try {
			secretKey = DatatypeConverter.parseBase64Binary(ACCESSSECRET_KEY);

			return new SecretKeySpec(secretKey, signatureAlgorithm.getJcaName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "key생성 에러");
		}
	}

	private String encodingToken(String subject, Key secretKey, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		try {
			return Jwts.builder()
					.setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
					.signWith(secretKey, signatureAlgorithm)
					.compact();
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 생성 에러");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User accessTokenDecoding(String token) {
		try {
			Claims claims = decodingToken(token, ACCESSSECRET_KEY);
			return userRepository.findById(claims.getSubject()).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "존재하지 않는 유저");
			});

		} catch (Exception e) {
			throw e;
		}
	}

	private Claims decodingToken(String token, String key) {
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(DatatypeConverter.parseBase64Binary(key))
					.build()
					.parseClaimsJws(token)
					.getBody();

			return claims;
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			throw new HttpClientErrorException(HttpStatus.GONE, "토큰 만료");
		} catch (SignatureException | MalformedJwtException e) {
			e.printStackTrace();
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조");
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
		}
	}
}
