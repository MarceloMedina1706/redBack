package com.red.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

//import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.red.security.dto.JwtDto;
import com.red.security.entity.UsuarioPrincipal;
import com.red.security.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Autowired
	private UserService userService;
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generatedToken(Authentication authentication) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		long idUser = getIdUser(usuarioPrincipal.getUsername());
		return Jwts.builder()
				.setSubject(usuarioPrincipal.getUsername())
				.claim("roles", roles)
				.claim("idUser", idUser)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;
		}catch(MalformedJwtException e) {
			logger.error("token mal formado");
		}catch(UnsupportedJwtException e) {
			logger.error("token no soportado");
		}catch(ExpiredJwtException e) {
			logger.error("token expirado");
		}catch(IllegalArgumentException e) {
			logger.error("token vacio");
		}catch(SignatureException e) {
			logger.error("fail en la firma");
		}
		
		return false;
	}
	
	public String refreshToken(JwtDto jwtdto) throws java.text.ParseException{
		System.out.println("Token antes de provider: [" + jwtdto.getToken() + "]");
		JWT jwt = JWTParser.parse(jwtdto.getToken());
		System.out.println("En el provider, depues del parse");
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		String username = claims.getSubject();
		List<String> roles = (List<String>) claims.getClaim("roles");
		
		return Jwts.builder()
				.setSubject(username)
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		
	}
	
	private long getIdUser(String email) {
		
		return userService.getByEmail(email).get().getIdUser();
	}
}
