package com.red.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails{
	
	private String name;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UsuarioPrincipal(String name, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UsuarioPrincipal build(User user) {
		List<GrantedAuthority> auth = user.getRoles().stream().map(rol->new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
		
		return new UsuarioPrincipal(user.getName(), user.getEmail(), user.getPassword(), auth);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getName() {
		return name;
	}

	public void setNombre(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
