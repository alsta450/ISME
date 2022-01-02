package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.converter.JsonToClassConverter;
import com.db.DatabaseHelper;

@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.*")
@ComponentScan(basePackages = { "com.*" })
@EntityScan("com.*")   
public class Main {
	private static final int DEFAULT_PORT = 8081;
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Main.class);

		app.run(args);
		
		//JsonToClassConverter j = new JsonToClassConverter();
		
		//j.readJson();

	}

}
