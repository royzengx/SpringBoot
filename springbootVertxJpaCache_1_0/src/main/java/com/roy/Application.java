package com.roy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.Vertx;

@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    private Vertx vertx;

    /**
     * Create an {@link ObjectMapper} for use in (de)serializing objects to/from JSON
     * @return An instance of {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(new JsonFactory());
    }

    /**
     * A singleton instance of {@link Vertx} which is used throughout the application
     * @return An instance of {@link Vertx}
     */
    @Bean
    public Vertx getVertxInstance() {
		if (this.vertx == null) {
			this.vertx = Vertx.vertx();
		}
        return this.vertx;
    }
    
}
