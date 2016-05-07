/**
 * 
 */
package com.sjsu.cmpe282.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sjsu.cmpe282.entity.User;
import com.sjsu.cmpe282.entity.UserContext;
import com.sjsu.cmpe282.service.UserService;

/**
 * @author ruchira
 *
 */
@BaseRestController
public class UserController {

	@Autowired
	UserService service;

	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public boolean authenticateUser(@RequestBody User user) {
		return service.authenticateUser(user);
	}

	@RequestMapping(value = "/get/{user_id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public UserContext getUser(@PathVariable("user_id") String userId) {
		System.out.println("User Id ::: " + userId);
		return service.getUser(userId);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public UserContext updateUser(@RequestBody UserContext userContext) {
		return service.updateUser(userContext);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserContext createUser(@RequestBody UserContext userContext) {
		return service.createUser(userContext);
	}

	@RequestMapping(value = "/delete/{user_id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public boolean deleteUser(@PathVariable("user_id") String userId) {
		return service.deleteUser(userId);
	}

	@RequestMapping(value = "/pref/{user_id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<UUID> getUserPreferences(@PathVariable("user_id") String userId) {
		return service.getUserPreferences(userId);
	}
}
