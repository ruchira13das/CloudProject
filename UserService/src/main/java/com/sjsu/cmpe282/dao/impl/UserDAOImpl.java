/**
 * 
 */
package com.sjsu.cmpe282.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.sjsu.cmpe282.controller.exception.RecordNotFoundException;
import com.sjsu.cmpe282.dao.UserDAO;
import com.sjsu.cmpe282.entity.User;
import com.sjsu.cmpe282.entity.UserContext;
import com.sjsu.cmpe282.entity.UserPreferences;

/**
 * @author ruchira
 *
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private CassandraOperations operations;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.dao.UserDAO#authenticateUser(com.sjsu.cmpe282.entity
	 * .User)
	 */
	@Override
	public boolean authenticateUser(User user) {
		boolean isAuthenticated = false;
		String userId = user.getUserId();
		String password = user.getPassword();

		String cql = "select * from user where user_id = '" + userId
				+ "' allow filtering";
		User dbUser = operations.selectOne(cql, User.class);

		System.out.println(">>>> DB password: " + dbUser.getPassword()
				+ ", User password: " + password);

		if (dbUser != null && password.equals(dbUser.getPassword())) {
			isAuthenticated = true;
		}

		return isAuthenticated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sjsu.cmpe282.dao.UserDAO#getUser(java.lang.String)
	 */
	@Override
	public UserContext getUser(String userId) {
		System.out.println("Lookup user: " + userId);
		User user = operations.selectOneById(User.class, userId);

		if (user == null) {
			System.out.println("No user found by id: " + userId);
			throw new RecordNotFoundException("User: " + userId + " not found");
		}

		return new UserContext(user, getCompleteUserPreferences(userId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.dao.UserDAO#updateUser(com.sjsu.cmpe282.entity.User,
	 * java.util.List)
	 */
	@Override
	public UserContext updateUser(UserContext userContext) {

		try {
			// Update user
			User user = userContext.getUser();
			List<UserPreferences> preferences = userContext
					.getUserPreferences();

			System.out.println(">>>> 1 <<<<");

			User dbUser = getUser(user.getUserId()).getUser();
			dbUser = mergeUser(user, dbUser);

			System.out.println(">>>> 2 <<<< " + dbUser.getUserId());

			User updatedUser = operations.update(dbUser);

			System.out.println(">>>> 3 <<<< " + updatedUser.getUserId());

			// Update user preferences - Delete and then re-create
			List<UserPreferences> dbUserPreferences = operations.select(
					"select * from user_preferences where user_id = '"
							+ user.getUserId() + "' allow filtering",
					UserPreferences.class);

			System.out.println(">>>> 4 <<<<");

			if (dbUserPreferences != null && dbUserPreferences.size() > 0) {
				System.out.println(">>>> 5 <<<<");
				// Delete them
				operations.delete(dbUserPreferences);
			}

			for (UserPreferences preference : preferences) {
				preference.setUserId(updatedUser.getUserId());
				preference.setId(UUID.randomUUID());
			}

			// Now insert the new user preferences
			List<UserPreferences> updatedUserPreferences = operations
					.insert(preferences);

			return new UserContext(updatedUser, updatedUserPreferences);

		} catch (Exception e) {
			throw e;
		}
	}

	private User mergeUser(User user, User dbUser) {

		if (!StringUtils.isEmpty(user.getFirstName())) {
			dbUser.setFirstName(user.getFirstName());
		}

		if (!StringUtils.isEmpty(user.getLastName())) {
			dbUser.setLastName(user.getLastName());
		}

		if (!StringUtils.isEmpty(user.getPassword())) {
			dbUser.setPassword(user.getPassword());
		}

		dbUser.setCreateDate(new Date());

		return dbUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.dao.UserDAO#createUser(com.sjsu.cmpe282.entity.User,
	 * java.util.List)
	 */
	@Override
	public UserContext createUser(UserContext userContext) {
		User user = userContext.getUser();
		List<UserPreferences> preferences = userContext.getUserPreferences();

		try {
			// Update user
			getUser(user.getUserId());

			return null;

		} catch (RecordNotFoundException e) {
			// Confirmed that user doesn't exist. Now create it!
			if (user.getCreateDate() == null) {
				user.setCreateDate(new Date());
			}

			User newUser = operations.insert(user);

			System.out.println(">>>> User created: " + newUser.getUserId());

			UserContext newUserContext = new UserContext(newUser, null);

			// Create the user preferences
			if (newUser != null && preferences != null
					&& preferences.size() > 0) {
				for (UserPreferences preference : preferences) {
					// Set the id and user id into the UserPreference object
					preference.setId(UUID.randomUUID());
					preference.setUserId(newUser.getUserId());
				}

				List<UserPreferences> newUserPreferences = operations
						.insert(preferences);
				newUserContext.setUserPreferences(newUserPreferences);

				System.out.println(">>>> Preferences created for user: "
						+ newUser.getUserId());
			}

			return newUserContext;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.dao.UserDAO#getUserPreferences(com.sjsu.cmpe282.entity
	 * .User)
	 */
	@Override
	public List<UUID> getUserPreferences(String userId) {
		String cql = "select category_id from user_preferences where user_id = '"
				+ userId + "' allow filtering";
		System.out.println(">>>> getUserPreferences: CQL: " + cql);

		List<UUID> preferences = operations.queryForList(cql, UUID.class);

		for (UUID preference : preferences) {
			System.out.println(">>>> Preference Id: " + preference.toString());
		}

		return preferences;
	}

	@Override
	public void deleteUser(String userId) {
		operations.deleteById(User.class, userId);
	}

	private List<UserPreferences> getCompleteUserPreferences(String userId) {
		String cql = "select * from user_preferences where user_id = '"
				+ userId + "' allow filtering";

		List<UserPreferences> preferences = operations.select(cql,
				UserPreferences.class);

		return preferences;
	}

}
