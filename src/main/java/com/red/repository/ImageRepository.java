package com.red.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.red.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	
	public abstract Optional<Image> findByUrlImage(String urlImage);
	public abstract Optional<Image> findByIdImg(long idImg);
}
