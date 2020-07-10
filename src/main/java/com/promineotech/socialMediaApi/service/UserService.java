package com.promineotech.socialMediaApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.socialMediaApi.entity.User;
import com.promineotech.socialMediaApi.repository.UserRepository;
import com.promineotech.socialMediaApi.view.Following;

@Service
public class UserService {
/* register or create users
 * allow users to log in
 * allow users to follow other users
 * get a list of followed users for a given user
 * update a users profile picture
 */
	@Autowired
	private UserRepository userRepo;
	
	public User createUser(User user) {
		return userRepo.save(user);
	}
	
	//pass in user, check for existence, check for password, return user
	
	public User login(User user) throws Exception{
		User founduser = userRepo.findByUsername(user.getUsername());
		if (founduser != null && founduser.getPassword().equals(user.getPassword())) {
			return founduser;
		} else {
			throw new Exception ("Username or Password is wrong.");
		}
		
	}
	
	public Following follow(Long userId, Long followId) throws Exception {
		User user = userRepo.findOne(userId);
		User followed = userRepo.findOne(followId);
		if (user == null || followed == null) {
			throw new Exception ("User does not exist.");
		}
		user.getFollowing().add(followed);
		userRepo.save(user);
		return new Following(user);
	}
	
	public Following getFollowed(Long userId) throws Exception {
		if (userRepo.findOne(userId) == null) {
			throw new Exception ("User does not exist.");
		}
		return new Following(userRepo.findOne(userId));
	}
	
	public User updateProfilePic (Long userId, String profilePicUrl) {
		User user = userRepo.findOne(userId);
		user.setProfilePictureUrl(profilePicUrl);
		return userRepo.save(user);
	}
}























