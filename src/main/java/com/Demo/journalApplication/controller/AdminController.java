package com.Demo.journalApplication.controller;

import com.Demo.journalApplication.entitiy.User;
import com.Demo.journalApplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserService userService;

	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers(){
		List<User> all = userService.getAll();
		if(all!=null){
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("No Users Found.",HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create-admin-user")
	public ResponseEntity<?> createAdminUser(@RequestBody User user){
		try {
			userService.saveAdminUser(user);
			return new ResponseEntity<>(user,HttpStatus.CREATED);
		} catch (Exception e) {
			log.info("Error Log : ",e);
			return new ResponseEntity<>("An Error Occured while creating user.",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
