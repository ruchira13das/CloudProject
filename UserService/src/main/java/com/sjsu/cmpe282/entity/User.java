/**
 * 
 */
package com.sjsu.cmpe282.entity;

import java.util.Date;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author ruchira
 *
 */
@Table("user")
public class User {
	@PrimaryKey("user_id")
	private String userId;

	@Column("first_name")
	private String firstName;

	@Column("last_name")
	private String lastName;
	
	@Column("password")
	private String password;
	
	@Column("created")
	private Date createDate;

	/**
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param createDate
	 */
	public User(String userId, String firstName, String lastName,
			String password, Date createDate) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		
		if (createDate != null) {
			this.createDate = createDate;
		} else {
			this.createDate = new Date();
		}
	}

	/**
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param createDate
	 */
	public User(String userId, String firstName, String lastName,
			Date createDate) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.createDate = createDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", createDate=" + createDate + "]";
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 
	 */
	public User() {
		super();
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
