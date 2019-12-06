package com.github.renuevo.lsa;

import com.github.renuevo.lsa.service.LsaModelService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class LsaApplication implements CommandLineRunner {

	private final LsaModelService lsaModelService;

	public static void main(String[] args) {
		SpringApplication.run(LsaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		lsaModelService.LasExample();
	}
}
