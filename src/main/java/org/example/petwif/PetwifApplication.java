package org.example.petwif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetwifApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetwifApplication.class, args);
	}

}
