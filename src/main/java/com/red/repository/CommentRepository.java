package com.red.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.entity.Comment;
import com.red.entity.Post;
import com.red.security.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	public abstract List<Comment> findByIdPostOrderByDateCommentDesc(Post idPost);
	public abstract List<Comment> findByIdPostAndIdUserOrderByDateCommentDesc(Post idPost, User idUser);
}
