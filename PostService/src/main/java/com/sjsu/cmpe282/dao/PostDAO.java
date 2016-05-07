/**
 * 
 */
package com.sjsu.cmpe282.dao;

import java.util.List;
import java.util.UUID;

import com.sjsu.cmpe282.entity.Post;
import com.sjsu.cmpe282.entity.User;

/**
 * @author ruchira
 *
 */
public interface PostDAO {
	public Post createPost(Post post, String userId);
	
	public Post updatePost(Post post);
	
	public Post getPostById(UUID postId);
	
	public void submitRatingForPost(int rating, UUID postId);
	
	public List<Integer> getRatingsForPost(UUID postId);
	
	public List<Post> getPostsForUser(String userId);
	
	public boolean doesPostBelongToUser(UUID postId, String userId);
	
	public List<Post> getAllPosts();
	
	public List<Post> getRecentPosts();
	
	public void deletePost(UUID postId, String userId);
	
	public List<String> getCategories();
	
	public boolean doesPostExists(UUID postId);
}
