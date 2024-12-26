package com.Demo.journalApplication.service;

import com.Demo.journalApplication.entitiy.JournalEntry;
import com.Demo.journalApplication.entitiy.UserEntity;
import com.Demo.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepository;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	Logger log;

	public void saveUser(UserEntity userEntity){
		try {
			userEntity.setDate(LocalDateTime.now());
			userRepository.save(userEntity);
		}
		catch (Exception e){

			log.error("Exception : ",e);
		}
	}

	public void saveNewEntry(UserEntity userEntity){
		try {
			userEntity.setDate(LocalDateTime.now());
			userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
			userEntity.setRoles(List.of("USER"));
			userRepository.save(userEntity);
		}
		catch (Exception e){

			log.error("Exception : ",e);
		}
	}

	public List<UserEntity> getAll(){
		return userRepository.findAll();
	}
	public Optional<UserEntity> findById(ObjectId id){
		return userRepository.findById(id);
	}
	public void deleteById(ObjectId id){
		userRepository.deleteById(id);
	}

	public UserEntity findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public List<JournalEntry> findJournalsByUser(String userName){
		UserEntity user = userRepository.findByUserName(userName);
		return user.getJournals();
	}
}
