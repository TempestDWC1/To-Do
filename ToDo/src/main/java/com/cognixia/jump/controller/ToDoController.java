package com.cognixia.jump.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.ToDo;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.ToDoService;
import com.cognixia.jump.service.UserService;

import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Controller for accessing ToDo data")
@RequestMapping("api/user")
@RestController
public class ToDoController {

	@Autowired
	ToDoService toDoService;
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/add/ToDo/{username}")
	public ResponseEntity<?> addToDo(@PathVariable String username, @Valid @RequestBody ToDo toDo) throws ResourceNotFoundException{
		
		User user = userService.getUserByUsername(username);
		
		toDo.setUser(user);
		
		ToDo created = toDoService.createToDo(toDo);
		
		user.addToDo(toDo);
		
		return ResponseEntity.ok("toDo id = " + created.getId() + " has been created");
		
	}
	
	
	@DeleteMapping("/remove/ToDo/{id}")
	public ResponseEntity<?> deleteToDo(@PathVariable Long id) throws ResourceNotFoundException{
		
		ToDo deleted = toDoService.removeToDo(id);
		
		return ResponseEntity.ok("ToDo deleted with id = " + deleted.getId());
		
	}
	
	
	@DeleteMapping("/remove/all/ToDo/{username}")
	public ResponseEntity<?> deleteAllToDoFromUser(@PathVariable String username) throws ResourceNotFoundException{
		
		User user = userService.getUserByUsername(username);
		
		toDoService.removeAllToDosFromUser(user.getId());
		
		return ResponseEntity.ok("Successfully removed all ToDos for user: " + username);
		
	}
	
	
	@PatchMapping("/ToDo/isComplete/{id}")
	public ResponseEntity<?> changeToDoCompletion(@PathVariable Long id) throws ResourceNotFoundException{
		
		ToDo change = toDoService.updateCompletion(id);
		
		return ResponseEntity.ok("Completion Status changed is now set to " + change.isComplete());
		
	}
	
	
	@PutMapping("/CompleteAll/ToDo/{username}")
	public ResponseEntity<?> changeAllToDoCompletion(@PathVariable String username) throws ResourceNotFoundException{
		
		User user = userService.getUserByUsername(username);
		
		toDoService.updateAllCompletion(user.getId());
		
		return ResponseEntity.ok("Completion Status changed for all To Dos of " + username);
		
	}
	
}














