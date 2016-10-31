/**
 * 
 */
package com.roy.service;

import java.util.List;

import com.roy.domain.User;

/**
 * @author Roy
 *
 */
public interface UserService {
	
	/**
	 * Find user by name.
	 * @param user user
	 * @return user object with data.
	 */
	User findUserByName(String name);
	
	List<User> findAll();
	
	void updateUser(User user);
}
