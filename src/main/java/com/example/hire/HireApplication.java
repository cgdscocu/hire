package com.example.hire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HireApplication {

	public static void main(String[] args) {
		SpringApplication.run(HireApplication.class, args);
	}

}
