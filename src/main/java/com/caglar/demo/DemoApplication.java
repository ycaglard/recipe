package com.caglar.demo;

import com.caglar.demo.Models.Recipe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(RecipeRepository recipeRepository) {
		return (args) -> {
			// DataLoader handles CSV import. No manual test data insertion needed.
			System.out.println("Application started.");
		};
	}
}
