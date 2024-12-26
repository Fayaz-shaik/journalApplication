package com.Demo.journalApplication.repository;

import com.Demo.journalApplication.entitiy.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

	UserEntity findByUserName(String userName);
	UserEntity deleteByUserName(String userName);
}
