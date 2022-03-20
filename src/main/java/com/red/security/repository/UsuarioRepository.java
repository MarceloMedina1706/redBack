package com.red.security.repository;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.security.entity.User;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByIdUser(long idUser);
	boolean existsByEmail(String email);
	
	public HashSet<User> findByNameContaining(String name);
	public HashSet<User> findByLastNameContaining(String lastName);
}
