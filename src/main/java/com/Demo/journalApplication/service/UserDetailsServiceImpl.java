package com.Demo.journalApplication.service;

import com.Demo.journalApplication.entitiy.User;
import com.Demo.journalApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(user!=null){
			return org.springframework.security.core.userdetails.User.builder()
					.username(user.getUserName())
					.password(user.getPassword())
					.roles(user.getRoles().toArray(new String[0]))
					.build();
		}
//		else {
//			throw new UsernameNotFoundException("User Not Found with username : "+username);
//		}
		return null;
	}
}
