package com.red.security.dto;

import java.io.Serializable;

public class JwtDto implements Serializable{
	
	private String token;
	
	public JwtDto() {}
	
	public JwtDto(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	
}
