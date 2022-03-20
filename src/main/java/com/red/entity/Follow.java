package com.red.entity;

import javax.persistence.*;

import com.red.security.entity.User;

@Entity
public class Follow {
	
	public Follow(){}	
	
	public long getIdFollow() {
		return idFollow;
	}

	public void setIdFollow(long idFollow) {
		this.idFollow = idFollow;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}

	public User getIdUserFollower() {
		return idUserFollower;
	}

	public void setIdUserFollower(User idUserFollower) {
		this.idUserFollower = idUserFollower;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFollow;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idUser")
	private User idUser;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idUserFollower")
	private User idUserFollower;
}
