package com.red.dto;

public class ResponseUserDTO {
	
	
	public ResponseUserDTO() {}
	
	public ResponseUserDTO(long idUser, String name, String lastName, String email, String born) {
		
		this.idUser = idUser;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.born = born;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBorn() {
		return born;
	}
	public void setBorn(String born) {
		this.born = born;
	}
	public String getUrlProfilePicture() {
		return urlProfilePicture;
	}
	public void setUrlProfilePicture(String urlProfilePicture) {
		this.urlProfilePicture = urlProfilePicture;
	}
	
	private long idUser;
	private String name;
	private String lastName;
	private String email;
	private String born;
	private String urlProfilePicture;
	
}
