package com.red.entity;

import java.util.Date;

import javax.persistence.*;

import com.red.security.entity.User;

@Entity
public class Comment {
	
	public Comment(){}
	
	public long getIdComment() {
		return idComment;
	}

	public void setIdComment(long idComment) {
		this.idComment = idComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getDateComment() {
		return dateComment;
	}

	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
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
	private long idComment;
	
	private String comment;
	
	private Date dateComment;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idPost")
	private Post idPost;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idUser")
	private User idUser;
}
