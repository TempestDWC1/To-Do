package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ToDo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 10, max = 200)
	@NotBlank
	private String description;
	
	@Column(nullable = false)
	private boolean isComplete;
	
	@Column(columnDefinition = "When the task is due")
	private Date dueDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	
	public ToDo() {
		this(-1L, "N/A", false, new Date());
	}

	public ToDo(Long id,@Size(min = 10, max = 200) String description, @NotBlank boolean isComplete, Date dueDate) {
		super();
		this.id = id;
		this.description = description;
		this.isComplete = isComplete;
		this.dueDate = dueDate;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	@Override
	public String toString() {
		return "ToDo [id=" + id + ", description=" + description + ", isComplete=" + isComplete + ", dueDate="
				+ dueDate + ", user=" + user + "]";
	}
	
	
}