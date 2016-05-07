/**
 * 
 */
package com.sjsu.cmpe282.entity;

import java.util.UUID;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author ruchira
 *
 */
@Table("user_preferences")
public class UserPreferences {
	@PrimaryKey("id")
	private UUID id;
	
	@Column("category_id")
	private UUID categoryId;
	
	@Column("user_id")
	private String userId;

	/**
	 * 
	 */
	public UserPreferences() {
		super();
	}

	/**
	 * @param id
	 * @param categoryId
	 * @param userId
	 */
	public UserPreferences(UUID id, UUID categoryId, String userId) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.userId = userId;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the categoryId
	 */
	public UUID getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
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
	
}
