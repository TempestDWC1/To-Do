package com.cognixia.jump.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Controller for accessing User data")
@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@ApiOperation(value = "Get a User")
	@GetMapping("/user/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) throws ResourceNotFoundException {
		
		User found = service.getUserByUsername(username);
		
		return new ResponseEntity<User>(found, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/add/user")
	public ResponseEntity<?> addUser(@RequestBody AuthenticationRequest registeringUser) throws Exception{
		
		userService.createNewUser(registeringUser);
		
		return ResponseEntity.ok(registeringUser.getUsername() + " has been created!");
		
	}
	
	
	@PutMapping("/user")
	public ResponseEntity<User> updateUser (@Valid @RequestBody User user) throws ResourceNotFoundException{
		
		User updated = service.updateUser(user);
		
		return new ResponseEntity<User>(updated, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authenticationRequest.getUsername(), 
							authenticationRequest.getPassword() ));
			
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect Username or Password.", e);
		}catch(Exception e) {
			throw new Exception(e);
		}
		
		final UserDetails USER_DETAILS = myUserDetailsService
											.loadUserByUsername(authenticationRequest.getUsername());
		
		final String JWT = jwtTokenUtil.generateToken(USER_DETAILS);
		
		return ResponseEntity.ok(new AuthenticationResponse(JWT));
		
	}
	
}
