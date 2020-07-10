package com.promineotech.socialMediaApi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.socialMediaApi.repository.PostRepository;
import com.promineotech.socialMediaApi.repository.UserRepository;
import com.promineotech.socialMediaApi.entity.Post;
import com.promineotech.socialMediaApi.entity.User;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private UserRepository userRepo;
	
	public Iterable<Post> getAllPosts() {
		return postRepo.findAll();
	}
	
	public Post getPost(Long id) {
		return postRepo.findOne(id);
	}
	
	public Post updatePost (Post post, Long id) throws Exception {
		Post foundPost = postRepo.findOne(id);
		if (foundPost == null) {
			throw new Exception ("Post not found.");
		}
		foundPost.setContent(post.getContent());
		return postRepo.save(foundPost);
	}
	public Post createPost(Post post, Long userId) throws Exception {
		User user = userRepo.findOne(userId);
		if (user == null) {
			throw new Exception ("User not found");
		}
		post.setDate(new Date());
		post.setUser(user);
		return postRepo.save(post);
	}
	public void deletePost(Long postId) throws Exception {
		Post post = postRepo.findOne(postId);
		if (post == null) {
			throw new Exception ("User not found");
		}
		postRepo.delete(postId);
	}
}
