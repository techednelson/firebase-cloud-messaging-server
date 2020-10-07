package com.spring.boot.fcm.server.service;

import com.spring.boot.fcm.server.dao.UserDao;
import com.spring.boot.fcm.server.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = this.userDao.findByUsername(username);
		if (userEntity == null) {
			this.logger.error("Login error: " + username + " does not exist in the system");
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authorities = Objects.requireNonNull(userEntity)
			.getRoles()
			.stream()
			.map(role -> new SimpleGrantedAuthority(role.getAlias()))
			.peek(authority -> this.logger.info("Role: " + authority.getAuthority()))
			.collect(Collectors.toList());
		return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getEnabled(), true, true, true, authorities);
	}
}
