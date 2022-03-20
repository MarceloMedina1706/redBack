package com.red.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


import com.red.security.entity.User;

@Entity
public class Post {
	
	public Post() {}
	
	public Post(long idPost, String contenido, Image foto, Date fecha) {
		this.idPost = idPost;
		this.contenido = contenido;
		this.foto = foto;
		this.fecha = fecha;
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

	public Image getFoto() {
		return foto;
	}

	public void setFoto(Image foto) {
		this.foto = foto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}
	
	public List<LikePost> getLikesPost() {
		return likesPost;
	}

	public void setLikesPost(List<LikePost> likesPost) {
		this.likesPost = likesPost;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPost;
	
	private String contenido;
	
	//@ManyToOne(cascade= {CascadeType.ALL}, fetch= FetchType.EAGER)
	//@JoinTable(name="post_image", joinColumns=@JoinColumn(name="idPost"), inverseJoinColumns=@JoinColumn(name="image_id"))
	@ManyToOne(fetch= FetchType.EAGER, cascade= {CascadeType.ALL})
	@JoinColumn(name="image_id")
	private Image foto;
	
	private Date fecha;
	
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="idUser")
	private User idUser;
	
	@OneToMany(fetch=FetchType.LAZY, 
			mappedBy="idPost",
			cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<LikePost> likesPost;
	
	@OneToMany(fetch=FetchType.LAZY, 
			mappedBy="idPost",
			cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Comment> comments;
}
