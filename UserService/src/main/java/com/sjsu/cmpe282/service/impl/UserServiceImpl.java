/**
 * 
 */
package com.sjsu.cmpe282.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sjsu.cmpe282.controller.exception.ServiceException;
import com.sjsu.cmpe282.dao.UserDAO;
import com.sjsu.cmpe282.entity.User;
import com.sjsu.cmpe282.entity.UserContext;
import com.sjsu.cmpe282.entity.UserPreferences;
import com.sjsu.cmpe282.service.UserService;

/**
 * @author ruchira
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.service.UserService#authenticateUser(com.sjsu.cmpe282
	 * .entity.User)
	 */
	@Override
	public boolean authenticateUser(User user) {
		boolean isAuthenticated = false;
		if (user != null) {
			if (!StringUtils.isEmpty(user.getUserId())
					&& !StringUtils.isEmpty(user.getPassword())) {
				isAuthenticated = userDAO.authenticateUser(user);
			}
		}

		return isAuthenticated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sjsu.cmpe282.service.UserService#getUser(java.lang.String)
	 */
	@Override
	public UserContext getUser(String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new ServiceException(
					"Invalid request. User id is blank/null.");
		}

		// Block the pwd data
		UserContext userContext = userDAO.getUser(userId);
		userContext.getUser().setPassword("xxxxxxx");
		;

		return userContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.service.UserService#updateUser(com.sjsu.cmpe282.entity
	 * .User, java.util.List)
	 */
	@Override
	public UserContext updateUser(UserContext userContext) {
		if (userContext == null || userContext.getUser() == null) {
			throw new ServiceException("Invalid request. User is blank/null.");
		}

		return userDAO.updateUser(userContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.service.UserService#createUser(com.sjsu.cmpe282.entity
	 * .User, java.util.List)
	 */
	@Override
	public UserContext createUser(UserContext userContext) {
		if (!isValidCreateUserRequest(userContext.getUser(),
				userContext.getUserPreferences())) {
			throw new ServiceException("Invalid create user request.");
		}

		return userDAO.createUser(userContext);
	}

	private boolean isValidCreateUserRequest(User user,
			List<UserPreferences> preferences) {

		boolean isValid = false;

		if (user != null && !StringUtils.isEmpty(user.getUserId())
				&& !StringUtils.isEmpty(user.getPassword())) {
			for (UserPreferences preference : preferences) {
				if (preference.getCategoryId() != null) {
					isValid = true;
				}

				if (!isValid) {
					break;
				}
			}
		}

		System.out.println(">>>>> isValidCreateUserRequest: " + isValid);

		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.service.UserService#getUserPreferences(com.sjsu.cmpe282
	 * .entity.User)
	 */
	@Override
	public List<UUID> getUserPreferences(String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new ServiceException(
					"Invalid getPreferences request. User id is blank/null.");
		}

		return userDAO.getUserPreferences(userId);
	}

	@Override
	public boolean deleteUser(String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new ServiceException(
					"Invalid deleteUser request. User id is blank/null.");
		}

		try {
			userDAO.deleteUser(userId);
		} catch (Exception e) {
			throw new ServiceException("Error while deleting user: " + userId
					+ ". Details: " + e.getMessage());
		}

		return true;
	}
}
