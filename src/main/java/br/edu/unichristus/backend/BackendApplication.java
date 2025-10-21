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
                description = "Biblioteca virtual",
                contact = @Contact(
                        name = "Joéliton",
                        email = "joeliton001@gmail.com",
                        url = "https://lattes.cnpq.br/5684649300959949")
        )
)

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);
	}

}
