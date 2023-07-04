package com.example.data.jpa;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class AppInitializer implements InitializingBean {

	private AuthorRepository authorRepository;

	public AppInitializer(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		authorRepository.findByNameContainingIgnoreCase("name");
	}

}