package com.projectpokerrest.pokerrest.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
<<<<<<< HEAD
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
=======
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
>>>>>>> origin/develop

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
<<<<<<< HEAD
			AuthenticationException authException) throws IOException, ServletException {
		LOGGER.error("Unauthorized error: {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}
}
=======
                         AuthenticationException authException) throws IOException, ServletException {
		LOGGER.error("Unauthorized error: {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}
>>>>>>> origin/develop
