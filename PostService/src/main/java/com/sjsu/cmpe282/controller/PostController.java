package com.sjsu.cmpe282.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sjsu.cmpe282.entity.Post;
import com.sjsu.cmpe282.entity.User;
import com.sjsu.cmpe282.service.PostService;

@BaseRestController
public class PostController {

	@Autowired
	PostService service;

	@RequestMapping(value = "/add/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Post createPost(@RequestBody Post post, @PathVariable("user_id") String userId) {
		return service.addPost(post, userId);
	}

	@RequestMapping(value = "/get/{post_id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Post viewPost(@PathVariable("post_id") UUID postId) {
		return service.getPostById(postId);
	}

	@RequestMapping(value = "/update/{post_id}/user/{user_id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Post updatePost(@PathVariable("user_id") String userId,
			@PathVariable("post_id") UUID postId, @RequestBody Post post) {
		return service.updatePost(postId, userId, post);
	}

	@RequestMapping(value = "/delete/{post_id}/user/{user_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deletePost(
			@PathVariable("post_id") UUID postId,
			@PathVariable("user_id") String userId) {

		service.deletePost(postId, userId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/rate/{post_id}/{rating}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<String> ratePost(@PathVariable("post_id") UUID postId, @PathVariable("rating") int rating) {

		service.ratePost(postId, rating);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Post> listTopPosts() {
		return service.listTopPosts();
	}
	
	@RequestMapping(value = "/rate/get/{post_id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Double getRatingsForPost(@PathVariable("post_id") UUID postId) {
		return service.getPostRating(postId);
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getCategories() {
		return service.getCategories();
	}

}
