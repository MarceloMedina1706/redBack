package com.red.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.entity.LikePost;
import com.red.entity.Post;
import com.red.security.entity.User;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long>{

	LikePost findByIdPost(Post post);

	List<LikePost> findByIdUser(User user);

}
