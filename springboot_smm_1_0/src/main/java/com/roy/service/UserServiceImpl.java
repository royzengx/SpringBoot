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

import com.roy.domain.User;
import com.roy.mapper.UserMapper;

/**
 * @author Roy
 *
 */
@Service
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
		//User user = userMapper.find(user)(name);
		return null;
	}
	
	@CacheEvict(value = DEMO_CACHE_NAME,key = "'userInfo_'+#user.getName()")
	@Override
	@Transactional
	public void updateUser(User user) {
		User user1 = new User();
		user1.setName("bar");
		user1.setId(1);
		user1.setEmail("bar1@gmail.com");
		user1.setPassword("0b5897c445554f654488c4511ef9d084");
		this.userMapper.updateByPrimaryKeySelective(user1);
		user1.setId(1);
		user1.setEmail("bar2@gmail.com");
		userMapper.updateByPrimaryKeySelective(user1);
		//throw new RuntimeException("We create an exception. ");
		
	}
	
	@Cacheable(value=DEMO_CACHE_NAME,key="AllUser")
	@Override
	public List<User> findAll() {
		// Fetch page 2.
		// PageHelper.startPage(2, 20); 
		return userMapper.find(new User());
	}

}
