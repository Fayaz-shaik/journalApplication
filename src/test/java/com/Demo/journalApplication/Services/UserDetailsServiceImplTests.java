package com.Demo.journalApplication.Services;


import com.Demo.journalApplication.entitiy.User;
import com.Demo.journalApplication.repository.UserRepository;
import com.Demo.journalApplication.service.UserDetailsServiceImpl;
import com.Demo.journalApplication.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTests {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@MockitoBean
	private UserRepository userRepository;

//	@BeforeEach
//	void setUp(){
//		MockitoAnnotations.initMocks(this);
//	}

	@Test
	void loadUserByUsernameTests(){

//		when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(
//				User.builder().username("moni").password("moni").build());
//		UserDetails user =  userDetailsService.loadUserByUsername("ram");
//		Assertions.assertNotNull(user);
		User mockUserDetails = new User();
		mockUserDetails.setUserName("shafi");
		mockUserDetails.setPassword("shafi");
		when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(mockUserDetails);
		UserDetails user =  userDetailsService.loadUserByUsername("ram");
		Assertions.assertNotNull(user);
	}
}
