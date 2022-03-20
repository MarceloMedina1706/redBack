package com.red.entity;

import javax.persistence.*;

import com.red.security.entity.User;

@Entity
public class LikePost {
	
	public LikePost() {}
	
	public Long getIdLike() {
		return idLikePost;
	}

	public void setIdLike(Long idLike) {
		this.idLikePost = idLike;
	}
	
	public Post getIdPost() {
		return idPost;
	}

	public void setIdPost(Post idPost) {
		this.idPost = idPost;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLikePost;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idPost")
	private Post idPost;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idUser")
	private User idUser;
}
