package fr.formation.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.formation.app.config.SecurityConfig;

@SpringBootApplication
public class FormSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormSpringApplication.class, args);
	}

}
