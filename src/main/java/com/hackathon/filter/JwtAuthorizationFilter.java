package com.hackathon.filter;


import com.hackathon.domain.entity.User;
import com.hackathon.lib.ConfirmToken;
import com.hackathon.service.jwt.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {
	private HandlerExceptionResolver handlerExceptionResolver;
	private JwtServiceImpl jwtService;
	private ConfirmToken confirmToken;

	//
	public JwtAuthorizationFilter(HandlerExceptionResolver handlerExceptionResolver) {
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig.getServletContext());

		this.jwtService = ctx.getBean(JwtServiceImpl.class);
		this.confirmToken = ctx.getBean(ConfirmToken.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request1 = (HttpServletRequest) request;
		if (!request1.getMethod().equals("OPTIONS")) {
			try {
				String token = confirmToken.removeStartString(request1, "Bearer");
				if (token == null) {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "로그인이 필요합니다.");
				}
				User user = jwtService.accessTokenDecoding(token);
				request1.setAttribute("user", user);
				chain.doFilter(request, response);
			} catch (Exception e) {
				handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, e);
			}
		}

	}

}
