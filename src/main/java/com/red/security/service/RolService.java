package com.red.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.security.entity.Rol;
import com.red.security.enums.RolNombre;
import com.red.security.repository.RolRepository;

@Service
@Transactional
public class RolService {
	
	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
}
