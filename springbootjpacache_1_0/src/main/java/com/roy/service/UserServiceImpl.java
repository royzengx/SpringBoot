/**
 * 
 */
package com.roy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roy.domain.User;
import com.roy.repository.UserRepository;

/**
 * @author Roy
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserByName(String name) {
		User user = userRepository.findByName(name);
		return user;
	}
	
	@Override
	public void updateUser(User user) {
		userRepository.findUser(user.getName());
	}

	@Override
	public User findUser(String name) {
		return userRepository.findUser(name);
	}
}
