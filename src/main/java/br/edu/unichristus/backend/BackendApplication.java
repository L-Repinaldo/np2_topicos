package br.edu.unichristus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Documentacao API - TEPW 2025.2",
                version = "1.0",
                description = "DOCUMENTACAO DA API CONSTRUIDA EM SALA",
                contact = @Contact(
                        name = "JOELITON",
                        email = "joeliton001@gmail.com",
                        url = "https://www.exemplo.com")
        )
)

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);
	}

}
