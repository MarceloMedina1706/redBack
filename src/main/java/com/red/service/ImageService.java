package com.red.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.entity.Image;
import com.red.repository.ImageRepository;

@Service
public class ImageService{
	
	@Autowired
	private ImageRepository imageRepository;
	
	public Image getByUrl(String urlImage) {
		return this.imageRepository.findByUrlImage(urlImage).get();
	}
	
	public Image getByIdImg(long id) {
		return this.imageRepository.findByIdImg(id).get();
	}

	public void save(Image image) {
		imageRepository.save(image);
		
	}
}
