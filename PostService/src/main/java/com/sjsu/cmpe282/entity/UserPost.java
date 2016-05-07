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
@Table("user_posts")
public class UserPost {
	@PrimaryKey("id")
	private UUID id;
	
	@Column("user_id")
	private String userId;
	
	@Column("post_id")
	private UUID postId;

	/**
	 * @param id
	 * @param userId
	 * @param postId
	 */
	public UserPost(UUID id, String userId, UUID postId) {
		super();
		this.id = id;
		this.userId = userId;
		this.postId = postId;
	}

	/**
	 * @param userId
	 * @param postId
	 */
	public UserPost(String userId, UUID postId) {
		super();
		this.userId = userId;
		this.postId = postId;
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
	 * @return the postId
	 */
	public UUID getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(UUID postId) {
		this.postId = postId;
	}

	/**
	 * 
	 */
	public UserPost() {
		super();
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
	
}
