/**
 * 
 */
package com.sjsu.cmpe282.service;

import java.util.List;
import java.util.UUID;

import com.sjsu.cmpe282.entity.User;
import com.sjsu.cmpe282.entity.UserContext;

/**
 * @author ruchira
 *
 */
public interface UserService {

	public boolean authenticateUser(User user);

	public UserContext getUser(String userId);

	public UserContext updateUser(UserContext userContext);

	public UserContext createUser(UserContext userContext);

	public List<UUID> getUserPreferences(String userId);

	public boolean deleteUser(String userId);
}
