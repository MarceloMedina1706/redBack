package com.red.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.security.entity.User;
import com.red.entity.Follow;
import com.red.repository.FollowRepository;

@Service
public class FollowService {
	
	@Autowired
	private FollowRepository followRepository;
	
	public void saveFollow(Follow follow){
		followRepository.save(follow);
	}
	
	public void deleteFollow(Follow follow) {
		followRepository.delete(follow);
	}
	
	public Follow findFollow(User idUser, User idUserFollower) {
		
		return followRepository.findByIdUserAndIdUserFollower(idUser, idUserFollower);
		
	}
	
	public List<Follow> getFollowByIdUserFollower(User userFollower){
		
		return followRepository.findByIdUserFollower(userFollower);
	}
	
	public List<Follow> getFollowByIdUser(User idUser){
		
		return followRepository.findByIdUser(idUser);
	}
}
