/**
 * 
 */
package com.roy.user;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roy
 *
 */
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
	@Resource
    private SessionFactory sessionFactory;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return  "Hello~";
    }

	@RequestMapping(value = "/usercount", method = RequestMethod.GET)
	public void usercount(HttpServletResponse response) {
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery("select * from user");
		List<?> list = sqlQuery.list();
		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"recordcount\":\"" + list.size() + "\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
