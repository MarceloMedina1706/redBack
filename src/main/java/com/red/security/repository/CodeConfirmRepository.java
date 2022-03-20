package com.red.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.security.entity.CodeConfirm;

@Repository
public interface CodeConfirmRepository extends JpaRepository<CodeConfirm, Long>{
	
	public CodeConfirm findByCode(String code);
}
