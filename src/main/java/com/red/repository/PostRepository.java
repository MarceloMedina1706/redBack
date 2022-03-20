package com.red.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.red.entity.Post;
import com.red.security.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	public abstract List<Post> findByIdUserOrderByFechaDesc(User user);
	
	public abstract Post findByIdPost(long IdPost);
	
	@Query(value="SELECT * FROM post WHERE id_user = :myId OR id_user IN :ids ORDER BY fecha DESC", nativeQuery=true)
	public abstract List<Post> findPosts(@Param("myId") long myId, @Param("ids") List<Long> ids);
	
}
