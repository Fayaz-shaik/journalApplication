package com.Demo.journalApplication.repository;

import com.Demo.journalApplication.entitiy.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

	User findByUserName(String userName);
	User deleteByUserName(String userName);
}
