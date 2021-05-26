package com.projectpokerrest.pokerrest.security.controller;

import com.projectpokerrest.pokerrest.security.jwt.JwtAuthenticationRequest;
import com.projectpokerrest.pokerrest.security.jwt.JwtAuthenticationResponse;
import com.projectpokerrest.pokerrest.security.jwt.JwtTokenUtil;
import com.projectpokerrest.pokerrest.security.jwt.dto.JwtUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtUtils;

	@PostMapping(value = "public/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtAuthenticationRequest loginRequest) {

		// effettuo l'autenticazione
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// metto lo user nel contesto di spring security
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// genero il token
		String jwt = jwtUtils.generateJwtToken(authentication);

		// estraggo le info dal principal
		JwtUserDetailsImpl userDetails = (JwtUserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(new JwtAuthenticationResponse(jwt, userDetails.getUsername(), userDetails.getEmail(), roles));
	}

}