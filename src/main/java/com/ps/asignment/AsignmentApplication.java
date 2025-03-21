package com.ps.asignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "service", "com.ps.asignment"})
@EntityScan(basePackages = {"entity"})
@EnableJpaRepositories(basePackages = {"repository"})
public class AsignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsignmentApplication.class, args);
	}

}
