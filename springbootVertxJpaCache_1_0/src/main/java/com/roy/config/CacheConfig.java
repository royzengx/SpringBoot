/**
 * 
 */
package com.roy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Roy
 *
 */
@Configuration
public class CacheConfig {
	private Logger log = LoggerFactory.getLogger(CacheConfig.class);

	/**
	 * ehcache manager
	 * 
	 * @param bean
	 * @return
	 */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
		log.info("CacheConfiguration.ehCacheCacheManager()");
		return new EhCacheCacheManager(bean.getObject());
	}

	/*
	 * Base on the setting of shared, Spring create　ehcache via:<BR>
	 * CacheManager.create() or new CacheManager().
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		log.info("CacheConfiguration.ehCacheManagerFactoryBean()");
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

}
