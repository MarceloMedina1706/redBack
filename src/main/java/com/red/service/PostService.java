package com.red.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.entity.Post;
import com.red.repository.PostRepository;
import com.red.security.entity.User;

@Service
public class PostService {
	
	public List<Post> getPosts(long myId, List<Long>ids){
		return postRepository.findPosts(myId, ids);
	}
	
	public List<Post> getPostsByUser(User user){
		return postRepository.findByIdUserOrderByFechaDesc(user);
	}
	
	public void savePost(Post post) {
		postRepository.save(post);
	}

	public Post getPostByIdPost(long idPost) {
		return postRepository.findByIdPost(idPost);
	}
	
	public Post getLastPost(User user) {
		return postRepository.findByIdUserOrderByFechaDesc(user).get(0);
	}
	
	public void deletePostByIdPost(long idPost) {
		postRepository.deleteById(idPost);
	}
	
	
	@Autowired
	private PostRepository postRepository;

	
}
