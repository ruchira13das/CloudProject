/**
 * 
 */
package com.sjsu.cmpe282.entity;

import java.sql.Date;
import java.util.UUID;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author ruchira
 *
 */
@Table("post_category")
public class PostCategory {
	
	@PrimaryKey("id")
	private UUID id;

	@Column("name")
	private String categoryName;

	@Column("created")
	private Date createDate;

	/**
	 * @param id
	 * @param categoryName
	 * @param createDate
	 */
	public PostCategory(UUID id, String categoryName, Date createDate) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.createDate = createDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PostCategory [id=" + id + ", categoryName=" + categoryName
				+ ", createDate=" + createDate + "]";
	}

	/**
	 * 
	 */
	public PostCategory() {
		super();
	}
	
}
