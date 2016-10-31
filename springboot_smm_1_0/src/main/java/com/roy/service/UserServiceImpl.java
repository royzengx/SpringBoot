/**
 * 
 */
package com.roy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.roy.domain.User;
import com.roy.mapper.UserMapper;

/**
 * @author Roy
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	// define a cache key.
	public static final String CACHE_KEY = "'userInfo'";
	// user a cache strategy.
	public static final String DEMO_CACHE_NAME = "demo";
	
	@Autowired
	private UserMapper userMapper;
	
	/* (non-Javadoc)
	 * @see com.roy.service.UserService#findUserByName(com.roy.domain.User)
	 */
	@Cacheable(value=DEMO_CACHE_NAME,key="'userInfo_'+#name")
	@Override
	public User findUserByName(String name) {
		System.out.print("Missed. By database!");
		User user = userMapper.findUserByName(name);
		return user;
	}
	
	@CacheEvict(value = DEMO_CACHE_NAME,key = "'userInfo_'+#user.getName()")
	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public List<User> findAll() {
		// Fetch page 2.
		PageHelper.startPage(2, 20); 
		return userMapper.findAll();
	}

}
