package com.promineotech.socialMediaApi.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.promineotech.socialMediaApi.entity.User;
import com.promineotech.socialMediaApi.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

		private static String UPLOADED_FOLDER = "./pictures/";
		
		@Autowired
		private UserService service;
		
		@RequestMapping(value="/register", method=RequestMethod.POST)
		public ResponseEntity<Object> register (@RequestBody User user) {
			return new ResponseEntity<Object>(service.createUser(user), HttpStatus.CREATED);
		}

		@RequestMapping(value="/login", method=RequestMethod.POST)
		public ResponseEntity<Object> login (@RequestBody User user) {
			try {
				return new ResponseEntity<Object>(service.login(user), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}

		@RequestMapping(value="/{id}/follows", method=RequestMethod.GET)
		public ResponseEntity<Object> showFollowedUsers (@PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.getFollowed(id), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}

		@RequestMapping(value="/{userId}/follows/{followId}", method=RequestMethod.POST)
		public ResponseEntity<Object> follow (@PathVariable Long userId, @PathVariable Long followId) {
			try {
				return new ResponseEntity<Object>(service.follow(userId, followId), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}

		@RequestMapping(value="{userId}/profilePicture", method=RequestMethod.POST)
		public ResponseEntity<Object> singleFileUpload (@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
			if (file.isEmpty()) {
				return new ResponseEntity<Object>("Please uploada file.", HttpStatus.BAD_REQUEST);
			}
			try {
				String profilePicUrl = UPLOADED_FOLDER + file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				Path path = Paths.get(profilePicUrl);
				Files.write(path, bytes);
				return new ResponseEntity<Object>(service.updateProfilePic(userId, profilePicUrl), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}
