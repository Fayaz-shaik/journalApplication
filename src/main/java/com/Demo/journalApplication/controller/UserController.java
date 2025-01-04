package com.Demo.journalApplication.controller;

import com.Demo.journalApplication.entitiy.User;
import com.Demo.journalApplication.repository.UserRepository;
import com.Demo.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public UserService userService;

	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> allUsers = userService.getAll();
		if(allUsers.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
	}


	@PutMapping()
	public ResponseEntity<?> updateUser(@RequestBody User user){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		User existingUser = userService.findByUserName(userName);

		existingUser.setUserName(user.getUserName());
		existingUser.setPassword(user.getPassword());
		userService.saveNewEntry(existingUser);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping()
	public ResponseEntity<?> deleteUserById(){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		userRepository.deleteByUserName(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
