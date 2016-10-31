/**
 * 
 */
package com.roy.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roy
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@RequestMapping(value = "/Hello", method = RequestMethod.GET)
    String  getDemo(){
        return  "Hello UserController";
    }
}
