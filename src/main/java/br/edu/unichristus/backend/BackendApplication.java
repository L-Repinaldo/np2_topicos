package br.edu.unichristus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(

    title = "Biblioteca Virtual Pública API",
        version = "1.0",
        description = """
                API desenvolvida para fins acadêmicos — UniChristus 2025.2<br><br>
                👨‍💻 <b>Equipe de Desenvolvimento:</b><br>
                <b>Antônio Augusto</b> — Mat: 23.1.000495 — <a href='https://github.com/antoni0-august0' target='_blank'>GitHub</a><br>
                <b>Joéliton Oliveira</b> — Mat: 24.1.000522 — <a href='https://github.com/joeliton-oliveira' target='_blank'>GitHub</a><br>
                <b>Lucas Repinaldo</b> — Mat: 24.1.000022 — <a href='https://github.com/L-Repinaldo' target='_blank'>GitHub</a><br>
                """,
        contact = @Contact(
            name = "E-mail e diretorio do trabalho",
            email = "joeliton001@gmail.com",
            url = "https://github.com/L-Repinaldo/np2_topicos.git"
        )
    )
)
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
