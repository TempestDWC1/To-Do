package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, targetEntity = ToDo.class, cascade = CascadeType.ALL)
	private List<ToDo> toDos;

	
	public User() {
		this(-1L, "N/A", "N/A");
	}
	
	public User(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	
	public void addToDo(ToDo toDo) {
		this.toDos.add(toDo);
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ToDo> getToDos() {
		return toDos;
	}

	public void setToDos(List<ToDo> toDos) {
		this.toDos = toDos;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", toDos=" + toDos + "]";
	}
	
	
}