package com.red.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.entity.Comment;
import com.red.entity.Post;
import com.red.security.entity.User;
import com.red.repository.CommentRepository;

@Service
public class CommentService {
	
	public void saveComment(Comment comment) {
		commentRespository.save(comment);
	}
	
	public List<Comment> getCommentsPost(Post post){
		return commentRespository.findByIdPostOrderByDateCommentDesc(post);
	}
	
	public Comment getLastComment(Post post, User user) {
		return commentRespository.findByIdPostAndIdUserOrderByDateCommentDesc(post, user).get(0);
	}
	
	
	@Autowired
	private CommentRepository commentRespository;
}
