package com.roy;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Roy
 *
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws UnknownHostException {

		SpringApplication.run(Application.class, args);
	}
}
