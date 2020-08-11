package com.matiange.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CacheServiceFactoryBean {

	@Value("${redis.enable}")
	private String enable;

	@SuppressWarnings("unused")
	private Properties configuration;

	@Autowired
	private CacheServiceFactory factory;

	public CacheServiceFactoryBean(Properties configuration) {
		this.configuration = configuration;
	}

	@Bean
	public CacheService getObject() throws Exception {
		if (!"true".equals(enable) || null == factory) {
			return null;
		}
		return factory.createCacheService();
	}



	public Class<?> getObjectType() {
		return CacheService.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
