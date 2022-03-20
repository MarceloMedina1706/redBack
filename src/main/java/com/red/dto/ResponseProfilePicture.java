package com.red.dto;

public class ResponseProfilePicture {

	public ResponseProfilePicture() {}
	
	public ResponseProfilePicture(String urlProfilePicture) {
		this.urlProfilePicture = urlProfilePicture;
	}

	public String getUrlProfilePicture() {
		return urlProfilePicture;
	}

	public void setUrlProfilePicture(String urlProfilePicture) {
		this.urlProfilePicture = urlProfilePicture;
	}

	private String urlProfilePicture;
}
