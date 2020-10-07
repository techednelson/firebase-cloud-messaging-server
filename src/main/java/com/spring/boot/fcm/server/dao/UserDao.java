package com.spring.boot.fcm.server.dao;

import com.spring.boot.fcm.server.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}
