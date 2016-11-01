/**
 * 
 */
package com.roy.service;

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
	
	void updateUser(User user);
	
	User findUser(String name);
}
