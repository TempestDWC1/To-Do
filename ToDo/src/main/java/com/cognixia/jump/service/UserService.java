package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserAlreadyExistsException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public User getUserByUsername(String username) throws ResourceNotFoundException{
		
		Optional<User> found = repo.findByUsername(username);
		
		// Check to see if there is an object in the Optional
		// check to see if object password matches with one given
		if( found.isPresent() ) {
			return found.get();
		}
		
		throw new ResourceNotFoundException("No user with username = " + username);
		
	}
	
	
	public boolean createNewUser(AuthenticationRequest registeringUser) throws Exception{
		
		Optional<User> isAlreadyRegistered = repo.findByUsername(registeringUser.getUsername());
		
		if( isAlreadyRegistered.isPresent() ) {
			
			throw new UserAlreadyExistsException(registeringUser.getUsername());
			
		}
		
		User newUser = new User();
		newUser.setUsername(registeringUser.getUsername());
		newUser.setPassword(passwordEncoder.encode( registeringUser.getPassword() ));
		
		repo.save(newUser);
		return true;
		
	}
	
	
	public User updateUser(User user) throws ResourceNotFoundException{
		
		if( repo.existsById(user.getId()) ) {
			
			User updated = repo.save(user);
			
			return updated;
		}
		
		throw new ResourceNotFoundException("User with id = " + user.getId() + 
				" could not be found. No update performed");
		
	}
	
	
}
