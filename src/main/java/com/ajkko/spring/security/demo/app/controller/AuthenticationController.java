package com.ajkko.spring.security.demo.app.controller;

import com.ajkko.spring.security.demo.app.config.JwtTokenHelper;
import com.ajkko.spring.security.demo.app.entity.AuthenticationRequest;
import com.ajkko.spring.security.demo.app.entity.LoginResponse;
import com.ajkko.spring.security.demo.app.entity.User;
import com.ajkko.spring.security.demo.app.entity.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenHelper jwtTokenHelper;
	private final UserDetailsService userDetailsService;

	public AuthenticationController(AuthenticationManager authenticationManager,
									JwtTokenHelper jwtTokenHelper,
									UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenHelper = jwtTokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
		
		LoginResponse response = new LoginResponse();
		response.setToken(jwtToken);

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/auth/userinfo")
	public ResponseEntity<?> getUserInfo(Principal user){
		User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
		
		UserDto userDto = new UserDto();
		userDto.setFirstName(userObj.getFirstName());
		userDto.setLastName(userObj.getLastName());
		userDto.setRoles(userObj.getAuthorities().toArray());

		return ResponseEntity.ok(userDto);
	}
}
