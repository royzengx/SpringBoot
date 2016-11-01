/**
 * 
 */
package com.roy.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.roy.domain.User;
import com.roy.service.UserService;

/**
 * @author Roy
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Resource
    private UserService userServices;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return  "Hello~";
    }

	@RequestMapping(value = "/finduser", method = RequestMethod.GET)
	public User findUserByName() {
		log.debug(new Date() + " Find user by name. start....");
		
		User user = new User();
		try {
			String name = "zxy2013";
			user = this.userServices.findUserByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.debug(new Date() + " Find user by name. end....");
		
		return user;
	}
	
	@RequestMapping(value = "/finduser1", method = RequestMethod.GET)
	public User findUserByName1() {
		log.debug(new Date() + " Find user by name. start....");
		
		User user = new User();
		try {
			String name = "zxy2013";
			user = this.userServices.findUser(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.debug(new Date() + " Find user by name. end....");
		
		return user;
	}
	
	@RequestMapping(value = "/updateuser", method = RequestMethod.GET)
	public User updateUser() {
		User user = new User();
		try {
			String name = "zxy2013";
			user.setName(name);
			user.setId(47);
			user.setIp("221.217.37.208");
			user.setPassword("0b5897c445554f654488c4511ef9d084");
			this.userServices.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
