package com.hackathon.service.auth;

import com.hackathon.domain.dto.auth.LoginDto;
import com.hackathon.domain.response.auth.LoginRO;
import com.hackathon.lib.Crypto;
import com.hackathon.service.jwt.JwtService;
import com.hackathon.service.school.SchoolService;
import com.hackathon.domain.dto.auth.SigninDto;
import com.hackathon.domain.entity.School;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JwtService jwtService;

	@Override
	@Transactional
	public void signin(SigninDto signinDto) {
		try {
			Optional<User> existUser = userRepo.findById(signinDto.getId());
			if (existUser.isPresent()) {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "사용중인 아이디입니다.");
			}

			signinDto.setPassword(Crypto.crypto(signinDto.getPassword()));
			School school = schoolService.addSchool(signinDto.getSchoolCode(), signinDto.getSchoolName());

			User user = new User(signinDto, school);
			userRepo.save(user);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public LoginRO login(LoginDto loginDto) {
		try {
			User user = userRepo.findById(loginDto.getId()).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "유저가 존재하지 않습니다.");
			});
			if (!user.getPassword().equals(Crypto.crypto(loginDto.getPassword()))) {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "비밀번호가 다릅니다.");
			}

			String accessToken = makeTokens(user.getId());

			return new LoginRO(accessToken, user.getSchool());
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void checkExist(String id) {
		try {
			Optional<User> existUser = userRepo.findById(id);
			if (existUser.isPresent()) {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "사용중인 아이디입니다.");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	public String makeTokens(String subject) {
		try {
			long accessTokenTime = 7 * 24 * 60 * 60 * 1000L;
			return jwtService.createToken(subject, accessTokenTime);

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
