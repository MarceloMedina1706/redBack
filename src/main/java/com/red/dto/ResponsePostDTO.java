package com.red.dto;

import java.util.Date;


public class ResponsePostDTO {
	
	public ResponsePostDTO() {};
	
	public ResponsePostDTO(long idUser, String namesUser, long idPost, String contenido, String foto, Date fecha) {
		this.idUser = idUser;
		this.namesUser = namesUser;
		this.idPost = idPost;
		this.contenido = contenido;
		this.foto = foto;
		this.fecha = fecha;
	}
	
	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getNamesUser() {
		return namesUser;
	}

	public void setNamesUser(String namesUser) {
		this.namesUser = namesUser;
	}

	public long getIdPost() {
		return idPost;
	}

	public void setIdPost(long idPost) {
		this.idPost = idPost;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	private long idUser;
	
	private String namesUser;
	
	private long idPost;
	
	private String contenido;
	
	private String foto;
	
	private Date fecha;
	
	
}
