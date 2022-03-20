package com.red.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.security.entity.Rol;
import com.red.security.enums.RolNombre;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	
	Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
