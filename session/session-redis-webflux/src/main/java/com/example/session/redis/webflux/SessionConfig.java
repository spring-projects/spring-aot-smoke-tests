package com.example.session.redis.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration(proxyBeanMethods = false)
@EnableRedisWebSession
class SessionConfig {

	@Bean
	LettuceConnectionFactory redisConnectionFactory() {
		String redisHost = System.getenv().getOrDefault("REDIS_HOST", "localhost");
		String port = System.getenv().getOrDefault("REDIS_PORT_6379", "6379");
		return new LettuceConnectionFactory(redisHost, Integer.parseInt(port));
	}

	@Bean
	@Qualifier("springSessionDefaultRedisSerializer")
	GenericJackson2JsonRedisSerializer redisSerializer(ObjectMapper objectMapper) {
		return new GenericJackson2JsonRedisSerializer(objectMapper);
	}

}
