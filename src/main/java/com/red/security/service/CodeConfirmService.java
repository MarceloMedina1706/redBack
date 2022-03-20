package com.red.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.security.entity.CodeConfirm;
import com.red.security.repository.CodeConfirmRepository;

@Service
public class CodeConfirmService {
	
	@Autowired
	private CodeConfirmRepository repository;
	
	public CodeConfirm getCodeConfirmByCode(String code) {
		return repository.findByCode(code);
	}
	
	public void saveCodeConfirm(CodeConfirm cc) {
		repository.save(cc);
	}
}
