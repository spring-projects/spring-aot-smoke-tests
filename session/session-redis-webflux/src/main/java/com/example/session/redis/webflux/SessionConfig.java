/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.session.redis.webflux;

import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson3JsonRedisSerializer;
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
	GenericJackson3JsonRedisSerializer redisSerializer(ObjectMapper objectMapper) {
		return new GenericJackson3JsonRedisSerializer(objectMapper);
	}

}
