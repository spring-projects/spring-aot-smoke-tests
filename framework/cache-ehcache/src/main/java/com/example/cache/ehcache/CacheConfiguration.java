package com.example.cache.ehcache;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

	@Bean
	public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
		return cacheManager -> {
			cacheManager.createCache("interface.invoke", cacheConfiguration());
			cacheManager.createCache("class.invoke", cacheConfiguration());
		};
	}

	private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
		return new MutableConfiguration<>().setStatisticsEnabled(true);
	}
}
