package com.red.dto;

import java.util.Date;

public class ResponseCommentDTO {
	
	private String usernames;
	private Date fecha;
	private String contenido;
	private long idUser;
	private long idPost;
	
	public ResponseCommentDTO() {}
	
	public ResponseCommentDTO(String usernames, Date fecha, String contenido, long idUser, long idPost) {
		
		this.usernames = usernames;
		this.fecha = fecha;
		this.contenido = contenido;
		this.idUser = idUser;
		this.idPost = idPost;
	}

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdPost() {
		return idPost;
	}

	public void setIdPost(long idPost) {
		this.idPost = idPost;
	}
	
}
