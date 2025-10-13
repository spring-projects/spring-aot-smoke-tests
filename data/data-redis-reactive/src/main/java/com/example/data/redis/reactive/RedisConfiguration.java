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

package com.example.data.redis.reactive;

import tools.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration(proxyBeanMethods = false)
class RedisConfiguration {

	@Bean
	ReactiveRedisTemplate<String, Person> redisOperations(ObjectMapper objectMapper,
			ReactiveRedisConnectionFactory connectionFactory) {
		JacksonJsonRedisSerializer<Person> serializer = new JacksonJsonRedisSerializer<>(objectMapper, Person.class);
		RedisSerializationContext.RedisSerializationContextBuilder<String, Person> builder = RedisSerializationContext
			.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, Person> context = builder.value(serializer).build();
		return new ReactiveRedisTemplate<>(connectionFactory, context);
	}

}
