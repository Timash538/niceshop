package com.niceshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class NiceshopApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NiceshopApplication.class, args);
	}

}
