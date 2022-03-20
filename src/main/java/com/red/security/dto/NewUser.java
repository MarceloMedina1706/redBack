package com.red.security.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewUser {
	
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String lastName;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String born;
	
	@NotBlank
	private String sex;
	
	
	public String getName() {
		return this.name;
	}
	public void setNombre(String name) {
		this.name = name;
	}
	public String getLastName() {
		return this.lastName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBorn() {
		return born;
	}
	public void setBorn(String born) {
		this.born = born;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
}
