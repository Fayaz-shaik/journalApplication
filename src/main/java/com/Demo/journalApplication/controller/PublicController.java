package com.Demo.journalApplication.controller;

import com.Demo.journalApplication.entitiy.User;
import com.Demo.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;

	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK";
	}

	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody User user){
		try {
			userService.saveNewEntry(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
