package com.sjsu.cmpe282.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author ruchira
 *
 */
@Table("post")
public class Post {

	@PrimaryKey("id")
	private UUID id;

	@Column("category")
	private String category;

	@Column("headline")
	private String headline;

	@Column("content")
	private String content;

	@Column("created")
	private Date createDate;

	@Column("updated")
	private Date updateDate;

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * @param headline
	 *            the headline to set
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param id
	 * @param category
	 * @param headline
	 * @param content
	 * @param rating
	 * @param createDate
	 * @param updateDate
	 */
	public Post(UUID id, String category, String headline, String content,
			Date createDate, Date updateDate) {
		super();
		this.id = id;
		this.category = category;
		this.headline = headline;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", category=" + category + ", headline="
				+ headline + ", content=" + content + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}

	/**
	 * @param category
	 * @param headline
	 * @param content
	 */
	public Post(String category, String headline, String content) {
		super();
		this.category = category;
		this.headline = headline;
		this.content = content;
	}

	/**
	 * 
	 */
	public Post() {
		super();
	}

}
