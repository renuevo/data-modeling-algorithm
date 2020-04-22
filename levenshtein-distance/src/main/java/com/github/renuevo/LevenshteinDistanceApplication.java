package com.github.renuevo;

import com.github.renuevo.service.LevenshteinService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@SpringBootApplication
public class LevenshteinDistanceApplication implements CommandLineRunner {

    private final LevenshteinService levenshteinService;

    public static void main(String[] args) {
        SpringApplication.run(LevenshteinDistanceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       levenshteinService.levenshteinExample();
    }
}
