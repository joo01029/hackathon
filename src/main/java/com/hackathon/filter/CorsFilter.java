package com.hackathon.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE,PATCH");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers", "X-Request-With, Content-Type, ContentType, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-header, Cache-Control, Pragma, Expires, Media-Type");
		res.setHeader("Access-Control-Expose-Headers", "content-disposition");
		res.setHeader("X-Content-Type-Options", "nosniff");
		if (req.getMethod().equals("OPTIONS"))
			res.setStatus(HttpServletResponse.SC_OK);

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

}

