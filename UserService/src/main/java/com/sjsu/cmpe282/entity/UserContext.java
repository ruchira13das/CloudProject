/**
 * 
 */
package com.sjsu.cmpe282.entity;

import java.util.List;

/**
 * @author ruchira
 *
 */
public class UserContext {

	private User user;
	private List<UserPreferences> userPreferences;

	/**
	 * 
	 */
	public UserContext() {
		super();
	}

	/**
	 * @param user
	 * @param userPreferences
	 */
	public UserContext(User user, List<UserPreferences> userPreferences) {
		super();
		this.user = user;
		this.userPreferences = userPreferences;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userPreferences
	 */
	public List<UserPreferences> getUserPreferences() {
		return userPreferences;
	}

	/**
	 * @param userPreferences
	 *            the userPreferences to set
	 */
	public void setUserPreferences(List<UserPreferences> userPreferences) {
		this.userPreferences = userPreferences;
	}

}
