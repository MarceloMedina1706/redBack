package com.red.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.red.security.entity.User;
import com.red.security.entity.UsuarioPrincipal;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.getByEmail(email).get();
		return UsuarioPrincipal.build(user);
	}

}
