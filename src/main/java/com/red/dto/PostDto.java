package com.red.dto;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property="imagen")
@JsonSubTypes.Type(value = File.class, name = "MultipartFile")*/
public class PostDto {
	
	private String contenido;
	
	private MultipartFile imagen;
	
	public PostDto() {}
	
	public PostDto(String contenido, MultipartFile imagen) {
		this.contenido = contenido;
		this.imagen = imagen;
	}
	
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}
	
	
	
}


