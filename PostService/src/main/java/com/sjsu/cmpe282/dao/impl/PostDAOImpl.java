/**
 * 
 */
package com.sjsu.cmpe282.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe282.controller.exception.RecordNotFoundException;
import com.sjsu.cmpe282.dao.PostDAO;
import com.sjsu.cmpe282.entity.Post;
import com.sjsu.cmpe282.entity.PostRatings;
import com.sjsu.cmpe282.entity.UserPost;

/**
 * @author ruchira
 *
 */
@Repository
public class PostDAOImpl implements PostDAO {

	@Autowired
	private CassandraOperations operations;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.dao.PostDAO#createPost(com.sjsu.cmpe282.entity.Post)
	 */
	@Override
	public Post createPost(Post post, String userId) {
		if (post.getId() == null) {
			post.setId(UUID.randomUUID());
		}

		try {
			post.setCreateDate(new Date());
			post.setUpdateDate(new Date());

			// Create the post
			post = operations.insert(post);

			System.out.println(">>>>>Newly created post id: " + post.getId());

			UserPost userPost = new UserPost(UUID.randomUUID(), userId,
					post.getId());
			PostRatings rating = new PostRatings(UUID.randomUUID(),
					post.getId(), 0);

			System.out.println(">>>>> 1 <<<<<");

			// Associate the post with the user
			operations.insert(userPost);

			// Create a blank entry in the ratings table
			operations.insert(rating);

			return post;
		} catch (Exception e) {
			// TODO: Programmatically rollback the transaction
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sjsu.cmpe282.dao.PostDAO#updatePost(com.sjsu.cmpe282.entity.Post)
	 */
	@Override
	public Post updatePost(Post post) {
		Post postInDB = getPostById(post.getId());
		if (postInDB != null) {
			return operations.update(mergeChanges(postInDB, post));
		} else {
			throw new RecordNotFoundException(post.toString()
					+ " not found in database!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sjsu.cmpe282.dao.PostDAO#getPost(int)
	 */
	@Override
	public Post getPostById(UUID postId) {
		Post post = operations.selectOneById(Post.class, postId);

		System.out.println(">>>>> getPostById: " + post);

		if (post == null) {
			throw new RecordNotFoundException("Post: " + postId + " not found");
		}

		return post;
	}

	/**
	 * @param dbRecord
	 * @param input
	 * @return Merge the request object with the DB record for the update
	 */
	private Post mergeChanges(Post dbRecord, Post input) {
		if (isFieldUpdated(input.getHeadline())) {
			dbRecord.setHeadline(input.getHeadline());
		}

		if (isFieldUpdated(input.getContent())) {
			dbRecord.setContent(input.getContent());
		}

		if (isFieldUpdated(input.getCategory())) {
			dbRecord.setCategory(input.getCategory());
		}

		dbRecord.setUpdateDate(new Timestamp((new Date()).getTime()));

		return dbRecord;
	}

	/**
	 * @param value
	 * @return Check if the request has the update for the field
	 */
	private boolean isFieldUpdated(String value) {
		return (value != null && !"".equals(value.trim())) ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sjsu.cmpe282.dao.PostDAO#getAllPosts()
	 */
	@Override
	public List<Post> getAllPosts() {
		List<Post> postsList = operations.select("select * from post",
				Post.class);

		if (postsList == null || postsList.size() == 0) {
			throw new RecordNotFoundException("POST table is empty");
		}

		return postsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sjsu.cmpe282.dao.PostDAO#getRecentPosts()
	 */
	@Override
	public List<Post> getRecentPosts() {
		String cql = "SELECT * FROM post LIMIT 10";
		return operations.selectAll(Post.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sjsu.cmpe282.dao.PostDAO#deletePost(UUID, String)
	 */
	@Override
	public void deletePost(UUID postId, String userId) {
		if (doesPostExists(postId)) {
			// Delete from POST table
			operations.deleteById(Post.class, postId);

			// Delete from USER_POST table
			// Delete is not supported on secondary index. So, a slightly
			// convoluted path for the deletes
			UserPost userPost = operations.selectOne(
					"select * from user_posts where post_id=" + postId
							+ "and user_id='" + userId + "' ALLOW FILTERING",
					UserPost.class);
			if (userPost != null) {
				System.out.println("Now deleting from user_posts table....");
				operations.delete(userPost);
				System.out.println("1: Delete successful!");
			}

			// DELETE from the POST_RATINGS table
			// Delete is not supported on secondary index. So, a slightly
			// convoluted path for the deletes
			List<PostRatings> postRatings = operations.select(
					"select * from post_ratings where post_id=" + postId
							+ " ALLOW FILTERING", PostRatings.class);
			System.out
					.println("Count of post Ratings: " + postRatings != null ? postRatings
							.size() : 0);
			if (postRatings != null && postRatings.size() > 0) {
				System.out.println("Now deleting from post_ratings table....");
				operations.delete(postRatings);
				System.out.println("2: Delete successful!");
			}

		} else {
			throw new RecordNotFoundException("Post: " + postId
					+ " not found in the database!");
		}
	}

	/**
	 * @param projectId
	 * @return true if the record exists in the table
	 */
	public boolean doesPostExists(UUID postId) {
		return operations.exists(Post.class, postId);
	}

	@Override
	public List<Post> getPostsForUser(String userId) {
		String cqlForPostIds = "SELECT post_id FROM user_posts where user_id='"
				+ userId.trim() + "'";

		System.out.println("getPostForUser: Query1: " + cqlForPostIds);

		List<UUID> postIds = operations.select(cqlForPostIds, UUID.class);

		if (postIds == null || postIds.size() == 0) {
			throw new RecordNotFoundException("No posts found for user: "
					+ userId);
		}

		String cqlForPosts = "SELECT * FROM post WHERE id IN ("
				+ flattenToString(postIds) + ") ORDER BY updated DESC";

		System.out.println("getPostForUser: Query2: " + cqlForPosts);

		return operations.select(cqlForPosts, Post.class);
	}

	private String flattenToString(List<UUID> postIds) {
		StringBuffer sb = new StringBuffer();

		int i = 1;
		int listSize = postIds.size();
		for (UUID postId : postIds) {

			if (i < listSize) {
				sb.append(postId.toString()).append(", ");
			} else {
				sb.append(postId.toString());
			}

			i++;
		}

		return sb.toString();
	}

	@Override
	public List<Integer> getRatingsForPost(UUID postId) {
		String cqlForRatings = "SELECT rating FROM post_ratings where post_id="
				+ postId.toString();
		System.out.println(">>>>>>> cqlForRatings: " + cqlForRatings);
		List<Integer> ratings = operations.queryForList(cqlForRatings,
				Integer.class);

		if (ratings == null || ratings.size() == 0) {
			throw new RecordNotFoundException(
					"No ratings data found for post: " + postId);
		}

		return ratings;
	}

	@Override
	public void submitRatingForPost(int rating, UUID postId) {
		PostRatings postRating = new PostRatings(UUID.randomUUID(), postId,
				rating);
		operations.insert(postRating);
	}

	@Override
	public boolean doesPostBelongToUser(UUID postId, String userId) {
		String cql = "SELECT post_id FROM user_posts WHERE user_id = '"
				+ userId.toString() + "' AND post_id=" + postId.toString()
				+ " ALLOW FILTERING";
		UUID postIdFromDB = operations.queryForObject(cql, UUID.class);// .selectOne(cql,
																		// UUID.class);

		System.out.println(">>>>>>> postIdFromDB: " + postIdFromDB);

		if (postIdFromDB != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<String> getCategories() {
		List<String> categoriesList = operations.queryForList(
				"select name from post_category", String.class);

		if (categoriesList == null || categoriesList.size() == 0) {
			throw new RecordNotFoundException("POST_CATEGORY table is empty");
		}

		return categoriesList;
	}
}
