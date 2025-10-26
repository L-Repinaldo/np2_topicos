package com.biblioteca.np2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Sistema de Biblioteca - NP2",
        version = "2.0",
        description = """
                API REST completa para gerenciamento de biblioteca.<br><br>
                
                <b>Funcionalidades:</b><br>
                • CRUD de Livros, Autores, Categorias e Editoras<br>
                • Gerenciamento de Usuários<br>
                • Busca de livros na Google Books API<br>
                • Relacionamentos automáticos (Find or Create)<br><br>
                
                <b>Projeto Acadêmico - Tópicos Web</b><br>
                UniChristus 2025.2
                """,
        contact = @Contact(
            name = "Repositório do Projeto",
            url = "https://github.com/L-Repinaldo/np2_topicos.git"
        )
    )
)
@SpringBootApplication
public class Np2Application {

	public static void main(String[] args) {
		SpringApplication.run(Np2Application.class, args);
	}

}
