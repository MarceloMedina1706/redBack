package com.red.security.controler;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.red.entity.Image;
import com.red.mailSender.CustomMailSender;
import com.red.security.dto.JwtDto;
import com.red.security.dto.LoginUser;
import com.red.security.dto.NewUser;
import com.red.security.entity.CodeConfirm;
import com.red.security.entity.Rol;
import com.red.security.entity.User;
import com.red.security.enums.RolNombre;
import com.red.security.jwt.JwtProvider;
import com.red.security.service.CodeConfirmService;
import com.red.security.service.RolService;
import com.red.security.service.UserService;
import com.red.service.ImageService;

@RestController
@RequestMapping("/red/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	CustomMailSender cms;
	
	@Autowired
	CodeConfirmService codeService;
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping("/new")
	public ResponseEntity<?> newRedUser(@RequestBody NewUser newUser){
		
		if(userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity("Ese email ya existe", HttpStatus.BAD_REQUEST);
		}
		
		Image image = imageService.getByIdImg(1);
		User user = new User(newUser.getName(), newUser.getLastName(), 
				newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()), newUser.getBorn(),
				newUser.getSex(), false);
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		user.setRoles(roles);
		user.setProfilePicture(image);
		userService.save(user);
		CodeConfirm codeConfirm = userService.generateCodeConfirm(user);
		cms.sendCodeConfirm(codeConfirm);
		return new ResponseEntity(true, HttpStatus.CREATED); 
				
	}
	
	@PostMapping("/confirm")
	public ResponseEntity<Boolean> registrationConfirm(@RequestBody String code){
		
		CodeConfirm cc = codeService.getCodeConfirmByCode(code);
		if(cc == null) {
			return new ResponseEntity(false, HttpStatus.NOT_FOUND);
		}
		
		Calendar cal = Calendar.getInstance();
		if((cc.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return new ResponseEntity(false, HttpStatus.UPGRADE_REQUIRED);
		}
		
		if(cc.getEnable()) {
			
			User user = userService.getById(cc.getUser().getIdUser()).get();
			
			user.setEnabled(true);
			
			cc.setEnable(false);
			
			codeService.saveCodeConfirm(cc);
			
			userService.save(user);
			
			return new ResponseEntity(true, HttpStatus.OK);
			
		}else {
			return new ResponseEntity(false, HttpStatus.ALREADY_REPORTED);
		}
		
		
	}
	
	@PostMapping("/resend")
	public ResponseEntity<Boolean> resendCodeConfirm(@RequestBody String code){
		CodeConfirm newCodeConfirm = userService.generateNewCodeConfirm(code);
		cms.sendCodeConfirm(newCodeConfirm);
		return new ResponseEntity(true, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto>login(@Valid @RequestBody LoginUser loginUser, BindingResult br){
		if(br.hasErrors()) {
			return new ResponseEntity("Campo mal puesto", HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generatedToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity(jwtDto, HttpStatus.OK);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException{
		System.out.println("Token antes de provider: [" + jwtDto.getToken() + "]");
		String token = jwtProvider.refreshToken(jwtDto);
		System.out.println("Token despues de provider: [" + token + "]");
		JwtDto jwt = new JwtDto(token);
		return new ResponseEntity(jwt, HttpStatus.OK);
	}
	
}
