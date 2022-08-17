package com.example.cache.simple;

import org.springframework.cache.annotation.Cacheable;

interface TestService {

	@Cacheable(cacheNames = "invoke")
	void invoke();

}
