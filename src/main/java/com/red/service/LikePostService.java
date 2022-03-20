package com.red.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.red.entity.LikePost;
import com.red.entity.Post;
import com.red.repository.LikePostRepository;
import com.red.security.entity.User;

@Service
@Transactional
public class LikePostService {
	
	@Autowired
	private LikePostRepository likePostRepository;
	
	public void saveLikePost(LikePost likePost) {
		likePostRepository.save(likePost);
	}
	
	public void deleteLikePost(Post post) {
		LikePost likePost = likePostRepository.findByIdPost(post);
		likePostRepository.delete(likePost);
	}
	
	public List<LikePost> getLikesByUser(User user){
		return likePostRepository.findByIdUser(user);
	}
}
