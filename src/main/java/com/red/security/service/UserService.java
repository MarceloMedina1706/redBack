package com.red.security.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.security.entity.CodeConfirm;
import com.red.security.entity.User;
import com.red.security.repository.CodeConfirmRepository;
import com.red.security.repository.UsuarioRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	CodeConfirmRepository ccRepository;
	
	public Optional<User> getByEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public Optional<User> getById(long idUser){
		return usuarioRepository.findByIdUser(idUser);
	}
	
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public void save(User usuario) {
		usuarioRepository.save(usuario);
	}
	
	public HashSet<User> getSearch(String search){
		HashSet<User> name = usuarioRepository.findByNameContaining(search);
		HashSet<User> lastName = usuarioRepository.findByLastNameContaining(search);
		for(User user : lastName) {	
			name.add(user);
		}
		return name;
	}
	
	public CodeConfirm generateCodeConfirm(User user) {
		Random claseRandom = new Random();
	    Integer intCode = claseRandom.nextInt(99999999 - 10000000);
		CodeConfirm cc = new CodeConfirm(intCode.toString(), user);
		
		return ccRepository.save(cc);
	}
	
	public CodeConfirm generateNewCodeConfirm(String code) {
		Random claseRandom = new Random();
	    Integer intCode = claseRandom.nextInt(99999999 - 10000000); 
		//String confirCode = "" + intCode;
		CodeConfirm nCC = ccRepository.findByCode(code);
		nCC.updateCode(intCode.toString());
		nCC.setEnable(true);
		
		return ccRepository.save(nCC);
	}
}
