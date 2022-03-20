package com.red.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.entity.Follow;
import com.red.security.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>{
	
	public abstract Follow findByIdUserAndIdUserFollower(User idUser, User idUserFollower);
	
	public abstract List<Follow> findByIdUserFollower(User idUserFollower);
	
	public abstract List<Follow> findByIdUser(User idUser);
}
