package com.sjsu.cmpe282.service;

import java.util.List;
import java.util.UUID;

import com.sjsu.cmpe282.entity.Post;

public interface PostService {
	
	public Post addPost(Post post, String userId);
	
	public Post getPostById(UUID postId);
	
	public List<Post> getPostsForUser(String userId);
	
	public Post updatePost(UUID postId, String userId, Post post);
	
	public void deletePost(UUID postId, String userId);
	
	public void ratePost(UUID postId, int rating);
	
	public double getPostRating(UUID postId);
	
	public List<Post> listAllPosts();
	
	public List<Post> listTopPosts();
	
	public List<String> getCategories();
	
}
