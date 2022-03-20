package com.red.security.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.red.entity.Follow;
import com.red.entity.Image;
import com.red.entity.Comment;
import com.red.entity.Post;
import com.red.service.ImageService;

@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUser;
	@NotNull
	private String name;
	@NotNull
	private String lastName;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String born;
	@NotNull
	private String sex;
	
	/*@ManyToOne(fetch=FetchType.LAZY, 
	mappedBy="idUser",
	cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})*/
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinTable(name="user_image", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="image_id"))
	private Image profilePicture = new Image();
	@NotNull
	private boolean enabled;
	
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuario_rol", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY, 
			mappedBy="idUser",
			cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Post> posts;
	
	@OneToMany(fetch=FetchType.LAZY, 
			mappedBy="idUser",
			cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Comment> comments;
	
	@OneToMany(fetch=FetchType.LAZY, 
			mappedBy="idUser")
	private List<Follow> follows;
	
	public User() {}
	
	public User(@NotNull String name, @NotNull String lastName, @NotNull String email, @NotNull String password,
			@NotNull String born, @NotNull String sex, @NotNull boolean enabled) {
		
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.born = born;
		this.sex = sex;
		this.enabled = enabled;
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



	public Image getProfilePicture() {
		return profilePicture;
	}



	public void setProfilePicture(Image profilePicture) {
		this.profilePicture = profilePicture;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public List<Follow> getFollows() {
		return follows;
	}

	public void setFollows(List<Follow> follows) {
		this.follows = follows;
	}
	
	
}
