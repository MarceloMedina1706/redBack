package com.red.dto;

public class CommentDto {
	
	private long idPost;
	private String comment;

	public CommentDto() {}

	public CommentDto(long idPost, String comment) {
		this.idPost = idPost;
		this.comment = comment;
	}

	public long getIdPost() {
		return idPost;
	}

	public void setIdPost(long idPost) {
		this.idPost = idPost;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
