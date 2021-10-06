package com.cognixia.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.ToDo;
import com.cognixia.jump.repository.ToDoRepository;

@Service
public class ToDoService {

	@Autowired
	ToDoRepository repo;
	
	
	public List<ToDo> getToDosByUserId(Long id){
		return repo.findByUserId(id);
	}
	
	
	public ToDo createToDo(ToDo toDo) {
		
		toDo.setId(-1L);
		
		ToDo saved = repo.save(toDo);
		
		return saved;
		
	}
	
	
	public ToDo removeToDo(Long id) throws ResourceNotFoundException {
		
		if( repo.existsById(id) ) {
			
			ToDo removed = repo.getById(id);
			
			repo.delete(removed);
			
			return removed;
			
		}
		
		throw new ResourceNotFoundException("No To Do with id = " + id);
		
	}
	
	
	public void removeAllToDosFromUser(Long id){
		
		List<ToDo> toDelete = repo.findByUserId(id);
		
		for(ToDo td: toDelete) {
			repo.delete(td);
		}
		
	}
	
	
	public ToDo updateCompletion(Long id) throws ResourceNotFoundException {
		
		if( repo.existsById(id) ) {
			
			ToDo completionStatus = repo.getById(id);
			
			boolean change = completionStatus.isComplete();
			
			// ^= is a XOR so everytime this method is called it
			// will just flip the completion status
			change ^= true;
			
			completionStatus.setComplete(change);
			
			repo.save(completionStatus);
			
			return completionStatus;
			
		}
		
		throw new ResourceNotFoundException("ID = " + id + " doesn't exist");
		
	}
	
	
	public void updateAllCompletion(Long id){
		
		// get the list of users to dos
		List<ToDo> usersTodos = getToDosByUserId(id);
			
		// go through all to dos and makes sure they are complete
		// if not then change them
		for(ToDo td: usersTodos) {
			if(!td.isComplete()) {
				
				td.setComplete(true);
				
			}
		}
		
		repo.saveAll(usersTodos);
	
	}
	
	
}





















