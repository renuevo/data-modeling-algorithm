package com.github.renuevo;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class LevenshteinDistanceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LevenshteinDistanceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
