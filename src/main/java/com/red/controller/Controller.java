package com.red.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
//import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.red.dto.CommentDto;
import com.red.dto.PostDto;
import com.red.dto.ResponseCommentDTO;
import com.red.dto.ResponsePostDTO;
import com.red.dto.ResponseProfilePicture;
import com.red.dto.ResponseUserDTO;
import com.red.entity.Comment;
import com.red.entity.Follow;
import com.red.entity.Image;
import com.red.entity.Post;
import com.red.entity.LikePost;
import com.red.security.entity.User;
import com.red.security.entity.UsuarioPrincipal;
import com.red.security.service.UserService;
import com.red.service.CloudinaryService;
import com.red.service.CommentService;
import com.red.service.FollowService;
import com.red.service.ImageService;
import com.red.service.LikePostService;
import com.red.service.PostService;


@RestController
@RequestMapping("/red")
@CrossOrigin("http://localhost:4200")
public class Controller {
	
	//==================OBTENER USER========================================================================================
	@PostMapping(value= {"/user", "/user/{id}"})
	public ResponseEntity<ResponseUserDTO> getRedUser(@PathVariable(value="id", required=false) String idUser){
		
		User user;
		ResponseUserDTO rUser = new ResponseUserDTO();
		
		if(idUser != null) {
			user = userService.getById(Long.parseLong(idUser)).get();
			
		}else {
			user = this.getUser();
		}
		
		rUser.setIdUser(user.getIdUser());
		rUser.setName(user.getName());
		rUser.setLastName(user.getLastName());
		rUser.setEmail(user.getEmail());
		rUser.setBorn(user.getBorn());
		rUser.setUrlProfilePicture(user.getProfilePicture().getUrlImage());
		return new ResponseEntity(rUser, HttpStatus.OK);
	}
	//===================FIN OBTENER USER==============================================================================================
	
	//==================OBTENER USER POR POST========================================================================================
	@PostMapping("/getUserByPost")
	public ResponseEntity<ResponseUserDTO> getUserByPost(@RequestParam long idPost){
		
		Post post = postService.getPostByIdPost(idPost);
		User user = post.getIdUser();
		ResponseUserDTO rUser = new ResponseUserDTO();
		
		rUser.setIdUser(user.getIdUser());
		rUser.setName(user.getName());
		rUser.setLastName(user.getLastName());
		rUser.setEmail(user.getEmail());
		rUser.setBorn(user.getBorn());
		
		return new ResponseEntity(rUser, HttpStatus.OK);
	}
	//===================FIN OBTENER USER POR POST==============================================================================================
	//===================OBTENER FOTO DE PERFIL==============================================================================================
	@PostMapping(value= {"/profilePicture", "/profilePicture/{id}"})
	public ResponseEntity<?> getProfilePicture(@PathVariable(value="id", required=false) String idUser){
		
		if(idUser == null) {
			String url = this.getUser().getProfilePicture().getUrlImage();
			return new ResponseEntity(new ResponseProfilePicture(url), HttpStatus.OK);
		}
		else {
			User user = userService.getById(Long.parseLong(idUser)).get();
			String url = user.getProfilePicture().getUrlImage();
			return new ResponseEntity(new ResponseProfilePicture(url), HttpStatus.OK);
		}
	}
	//===================FIN OBTENER FOTO DE PERFIL==============================================================================================
	//===================ESTABLECER FOTO DE PERFIL==============================================================================================
	@PostMapping("/updateProfilePicture")
	public ResponseEntity<?> setProfilePicture(@RequestParam MultipartFile picture){
		//-----------------------------------------------------------
		User user = this.getUser();
		//-----------------------------------------------------------
		//----------------Imagen-------------------------------------
		Image img = null;
		
		if(picture != null) {
			try {
				
				BufferedImage bi = ImageIO.read(picture.getInputStream());
				System.out.println(bi.getType() );
				Map result = cloudinaryService.upload(picture);
		        img = new Image((String)result.get("original_filename"),
		                        (String)result.get("url"),
		                        (String)result.get("public_id"));
		        
		        imageService.save(img);
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		//-----------------------------------------------------------
		String url = "";
		
		if(img != null) {
			
			user.setProfilePicture(imageService.getByUrl(img.getUrlImage()));
			
			userService.save(user);
			
			url = user.getProfilePicture().getUrlImage();
		}
		
		return new ResponseEntity(new ResponseProfilePicture(url), HttpStatus.OK);
	}
	//===================FIN ESTABLECER FOTO DE PERFIL==============================================================================================
	//==================OBTENER POSTS========================================================================================
	@GetMapping("/posts")
	public ResponseEntity<List<ResponsePostDTO>> getPosts(){
		
		User user = this.getUser();
		
		List<Follow> follows = followService.getFollowByIdUserFollower(user);
		
		List<Long> ids = new ArrayList<Long>();
		
		follows.stream().forEach((f) -> {
			ids.add(f.getIdUser().getIdUser());
		});
		
		List<Post> posts = postService.getPosts(user.getIdUser(), ids);
		
		List<ResponsePostDTO> rPosts = new ArrayList<ResponsePostDTO>();
		posts.stream().forEach((p) -> {
			long idUser = p.getIdUser().getIdUser();
			String names = p.getIdUser().getName() + " " + p.getIdUser().getLastName();
			String foto = "";
			if(p.getFoto() != null) foto = p.getFoto().getUrlImage();
			rPosts.add(new ResponsePostDTO(idUser, names, p.getIdPost(), p.getContenido(), foto, p.getFecha()));
		});
		
		return new ResponseEntity(rPosts, HttpStatus.OK);
	}
	//===================FIN OBTENER POSTS==============================================================================================
	//==================OBTENER POSTS BY USER========================================================================================
	@PostMapping(value= {"/getPostsByUser", "/getPostsByUser/{id}"})
	public ResponseEntity<List<ResponsePostDTO>> getPostsByUser(@PathVariable(value="id", required=false) String idUser){
		
		User user;
		
		if(idUser != null) {
			user = userService.getById(Long.parseLong(idUser)).get();
			
		}else {
			user = this.getUser();
		}
		
		List<Follow> follows = followService.getFollowByIdUserFollower(user);
		
		List<Long> ids = new ArrayList<Long>();
		
		follows.stream().forEach((f) -> {
			ids.add(f.getIdUser().getIdUser());
		});
		
		List<Post> posts = postService.getPostsByUser(user);
		
		List<ResponsePostDTO> rPosts = new ArrayList<ResponsePostDTO>();
		
		posts.stream().forEach((p) -> {
			long idUserPost = p.getIdUser().getIdUser();
			String names = p.getIdUser().getName() + " " + p.getIdUser().getLastName();
			rPosts.add(new ResponsePostDTO(idUserPost, names, p.getIdPost(), p.getContenido(), p.getFoto().getUrlImage(), p.getFecha()));
		});
		
		return new ResponseEntity(rPosts, HttpStatus.OK);
	}
	//===================FIN OBTENER POSTS BY USER==============================================================================================
	//===================GUARDAR POST===========================================================================================
	@PostMapping("/savePost")
	public ResponseEntity<ResponsePostDTO> savePost(@RequestParam(required= false) String contenido, @RequestParam(required=false) MultipartFile imagen) {
		
		//-----------------------------------------------------------
		User user = this.getUser();
		//-----------------------------------------------------------
		//----------------Imagen-------------------------------------
		Image img = null;
		
		if(imagen != null) {
			try {
				
				BufferedImage bi = ImageIO.read(imagen.getInputStream());
				System.out.println(bi.getHeight() + " " + bi.getType() + "\n " + bi.toString() + "\n\n\n");
				Map result = cloudinaryService.upload(imagen);
		        img = new Image((String)result.get("original_filename"),
		                        (String)result.get("url"),
		                        (String)result.get("public_id"));
		        
		        imageService.save(img);
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		//-----------------------------------------------------------
		Post post = new Post();
		
		Calendar cal = Calendar.getInstance();
    	
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        
    	Date date = new Date(cal.getTime().getTime());
		
    	post.setFecha(date);
    	
		post.setContenido(contenido);
		 
		if(img != null)
			post.setFoto(imageService.getByUrl(img.getUrlImage()));
		else
			post.setFoto(null);
		
		post.setIdUser(user);
		
		postService.savePost(post);
		
		Post p = postService.getLastPost(user);
		
		String names = p.getIdUser().getName() + " " + p.getIdUser().getLastName();
		
		String urlImage = "";
		
		if(p.getFoto() != null) urlImage = p.getFoto().getUrlImage();
		
		ResponsePostDTO pr = new ResponsePostDTO(p.getIdUser().getIdUser(), names,
				p.getIdPost(), p.getContenido(), urlImage, p.getFecha());
		
		return new ResponseEntity(pr, HttpStatus.OK);
	}
	//================FIN GUARDAR POST====================================================================================
	//================EDITAR POST====================================================================================
	@PostMapping("/updatePost/{idPost}")
	public ResponseEntity<Boolean> updatePost(@PathVariable("idPost") long idPost, @RequestBody PostDto postDto) {
		Post post = postService.getPostByIdPost(idPost);
		post.setContenido(postDto.getContenido());
		//post.setFoto(imageService.getByUrl(postDto.getImagen()));
		
		postService.savePost(post);
		
		return new ResponseEntity(true, HttpStatus.OK);
	}
	//================FIN EDITAR POST====================================================================================
	//================ELIMINAR POST====================================================================================
	@PostMapping("/deletePost/{idPost}")
	public ResponseEntity<Boolean> deletePost(@PathVariable("idPost") long idPost) throws IOException {
		Post post = postService.getPostByIdPost(idPost);
		if(post.getFoto() != null) cloudinaryService.delete(post.getFoto().getIdImage());
		postService.deletePostByIdPost(idPost);
		
		return new ResponseEntity(true, HttpStatus.OK);
	}
	//================FIN ELIMINAR POST====================================================================================
	//================OBTENER COMENTARIOS POST====================================================================================
	@PostMapping("/getComments/{idPost}")
	public ResponseEntity<ResponseCommentDTO> getCommentPost(@PathVariable("idPost") long idPost){
		Post post = postService.getPostByIdPost(idPost);
		List<Comment> comments = commentService.getCommentsPost(post);
		List<ResponseCommentDTO> resComments = new ArrayList<ResponseCommentDTO>();
		
		comments.stream().forEach((comment) -> {
			String usernames = comment.getIdUser().getName() + " " + comment.getIdUser().getLastName();
			resComments.add(new ResponseCommentDTO(
						usernames, comment.getDateComment(), 
						comment.getComment(),
						comment.getIdUser().getIdUser(),
						comment.getIdPost().getIdPost()
					)
			);
		});
		
		return new ResponseEntity(resComments, HttpStatus.OK);
	}
	//================FIN OBTENER COMENTARIOS POST====================================================================================
	//================GUARDAR COMENTARIO====================================================================================
	@PostMapping("/saveComment")
	public ResponseEntity<ResponseCommentDTO> saveComment(@RequestBody CommentDto commentDto) {
		
		//-----------------------------------------------------------
		User user = this.getUser();
		Post post = postService.getPostByIdPost(commentDto.getIdPost());
		//-----------------------------------------------------------
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
    	Date date = new Date(cal.getTime().getTime());
    	//-----------------------------------------------------------
		
		Comment comment = new Comment();
		comment.setComment(commentDto.getComment());
		comment.setDateComment(date);
		comment.setIdPost(post);
		comment.setIdUser(user);
		
		commentService.saveComment(comment);
		Comment c = commentService.getLastComment(post, user);
		
		String usernames = user.getName() + " " + user.getLastName();
		ResponseCommentDTO rc = new ResponseCommentDTO(
				usernames, c.getDateComment(), c.getComment(), user.getIdUser(), post.getIdPost());
		
		return new ResponseEntity(rc, HttpStatus.OK);
	}
	//================FIN GUARDAR COMENTARIO====================================================================================
	//================GUARDAR LIKE====================================================================================
	@PostMapping("/likePost/{idPost}")
	public ResponseEntity<?> saveLikePost(@PathVariable("idPost") long idPost){
		
		//-----------------------------------------------------------
		User user = this.getUser();
		Post post = postService.getPostByIdPost(idPost);
		//-----------------------------------------------------------
		
		LikePost likePost = new LikePost();
		likePost.setIdPost(post);
		likePost.setIdUser(user);
		
		likePostService.saveLikePost(likePost);
		
		return new ResponseEntity("ok", HttpStatus.OK);
	}
	//================FIN GUARDAR LIKE====================================================================================
	//================BORRAR LIKE====================================================================================
	@PostMapping("/unLikePost/{idPost}")
	public ResponseEntity<?> deleteLikePost(@PathVariable("idPost") long idPost){
		//-----------------------------------------------------------
		Post post = postService.getPostByIdPost(idPost);
		//-----------------------------------------------------------
		likePostService.deleteLikePost(post); 
		
		return new ResponseEntity("ok", HttpStatus.OK);
	}
	//================FIN BORRAR LIKE====================================================================================
	//================OBTENER LIKES====================================================================================
	@PostMapping("/getLikes")
	public ResponseEntity<List<Long>> getLikes(){
		//-----------------------------------------------------------
		User user = this.getUser();
		//-----------------------------------------------------------
		List<Long> likes = new ArrayList<Long>();
		List<LikePost> lPosts = likePostService.getLikesByUser(user);
		lPosts.stream().forEach((lp) -> {
			likes.add(lp.getIdPost().getIdPost());
		});
		
		return new ResponseEntity(likes, HttpStatus.OK);
	}
	//================FIN OBTENER LIKES====================================================================================
	//================ FOLLOW ====================================================================================
	@PostMapping("/follow/{id}")
	public ResponseEntity<?> follow(@PathVariable("id") long idUser){
		//-----------------------------------------------------------
		User userFollowing = userService.getById(idUser).get();
		User userFollower = this.getUser();
		//-----------------------------------------------------------
		Follow follow = new Follow();
		follow.setIdUser(userFollowing);
		follow.setIdUserFollower(userFollower);
		
		followService.saveFollow(follow);
		
		return new ResponseEntity("ok", HttpStatus.OK);
	}
	//================FIN FOLLOW====================================================================================
	//================UNFOLLOW====================================================================================
	@PostMapping("/unFollow/{id}")
	public ResponseEntity<?> unFollow(@PathVariable("id") long id){
		//-----------------------------------------------------------
		User userFollowing = userService.getById(id).get();
		User userFollower = this.getUser();
		//-----------------------------------------------------------
		Follow follow = followService.findFollow(userFollowing, userFollower);
		System.out.println("UNFOLLOW     idFollow: " + follow.getIdFollow());
		followService.deleteFollow(follow);
		
		return new ResponseEntity("ok", HttpStatus.OK);
	}
	//================FIN UNFOLLOW====================================================================================
	//================UNFOLLOW====================================================================================
	@PostMapping("/verFollow/{id}")
	public ResponseEntity<Boolean> verFollow(@PathVariable("id") long idUser){
		//-----------------------------------------------------------
		User userFollowing = userService.getById(idUser).get();
		User userFollower = this.getUser();
		//-----------------------------------------------------------
		Follow follow = followService.findFollow(userFollowing, userFollower);
		if(follow != null) 
			return new ResponseEntity(true, HttpStatus.OK);
			
		return new ResponseEntity(false, HttpStatus.OK);
	}
	//================FIN UNFOLLOW====================================================================================
	//================GET FOLLOWING USERS====================================================================================
	@PostMapping(value={"/getFollowing"})
	public ResponseEntity<List<ResponseUserDTO>> getFollowing(@RequestParam(required=false) String idUser){
		//-----------------------------------------------------------
		User user;
		
		if(idUser == null) user = this.getUser();
		else user = userService.getById(Long.parseLong(idUser)).get();
		//-----------------------------------------------------------
		List<ResponseUserDTO> usersFollowing = new ArrayList<ResponseUserDTO>();
		List<Follow> following = followService.getFollowByIdUserFollower(user);
		
		following.stream().forEach((f) -> {
			User u = f.getIdUser();
			usersFollowing.add(new ResponseUserDTO(
						u.getIdUser(),
						u.getName(),
						u.getLastName(),
						u.getEmail(),
						u.getBorn()
					));
		});
			
		return new ResponseEntity(usersFollowing, HttpStatus.OK);
	}
	//================FIN GET FOLLOWING USERS====================================================================================
	//================GET FOLLOWERS USERS====================================================================================
	@PostMapping(value={"/getFollowers"})
	public ResponseEntity<Boolean> getFollowers(@RequestParam(required=false) String idUser){
		//-----------------------------------------------------------
		User user;
		
		if(idUser == null) user = this.getUser();
		else user = userService.getById(Long.parseLong(idUser)).get();
		//-----------------------------------------------------------
		List<ResponseUserDTO> usersFollowers = new ArrayList<ResponseUserDTO>();
		List<Follow> followers = followService.getFollowByIdUser(user);
		
		followers.stream().forEach((f) -> {
			User u = f.getIdUserFollower();
			usersFollowers.add(new ResponseUserDTO(
						u.getIdUser(),
						u.getName(),
						u.getLastName(),
						u.getEmail(),
						u.getBorn()
					));
		});
			
		return new ResponseEntity(usersFollowers, HttpStatus.OK);
	}
	//================FIN GET FOLLOWERS USERS====================================================================================
	//================RESULTADO====================================================================================
	@PostMapping("/search")
	public ResponseEntity<ResponseUserDTO> getSearch(@RequestBody String search){
		HashSet<User> users = userService.getSearch(search);
		HashSet<ResponseUserDTO> result = new HashSet<ResponseUserDTO>();
		
		users.stream().forEach((user) -> {
			result.add(new ResponseUserDTO(user.getIdUser(),
					user.getName(),
					user.getLastName(),
					user.getEmail(),
					user.getBorn()));
		});
			
		return new ResponseEntity(result, HttpStatus.OK);
	}
	//================FIN RESULTADO====================================================================================
	
	
	private User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		String email = usuarioPrincipal.getUsername();
		return userService.getByEmail(email).get();
	}
	
	
	
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private LikePostService likePostService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private CloudinaryService cloudinaryService;
}
