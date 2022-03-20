package com.red.entity;

import javax.persistence.*;

@Entity
public class Image {
	
	
	public Image() {}
	
	public Image(String name, String urlImage, String idImage) {
		
		this.name = name;
		this.idImage = idImage;
		this.urlImage = urlImage;
	}

	public long getIdImg() {
		return idImg;
	}
	public void setIdImg(long idImg) {
		this.idImg = idImg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdImage() {
		return idImage;
	}
	public void setIdImage(String idImage) {
		this.idImage = idImage;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idImg;
	
	private String name;
	private String idImage;
	private String urlImage;
	
	
	
}
