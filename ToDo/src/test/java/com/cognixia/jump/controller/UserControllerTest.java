package com.cognixia.jump.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.ToDo;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api/";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService service;
	
	@InjectMocks
	private UserController controller;
	
	
	@Test
	@WithMockUser(username = "test", password = "testPass")
	void testAuth() throws Exception{
	
		String uri = STARTING_URI + "user";
//		
//		List<ToDo> toDos = Arrays.asList(
//					new ToDo(1L, "I am a description", false, new Date()),
//					new ToDo(2L, "I like pineapples", false, new Date())
//				);
//		
//		List<User> allUsers = Arrays.asList(
//					new User(1L, "Geralt", "Rivia"),
//					new User(2L, "John", "Wick")
//				);
				
		mockMvc.perform(get(uri))
 			.andExpect(status().isOk());
		
	}
	
}



















