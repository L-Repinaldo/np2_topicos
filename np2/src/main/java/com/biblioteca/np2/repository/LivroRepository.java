package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("SELECT livros FROM Livro livros WHERE LOWER(livros.autor.nome) = (:nome) ")
    List<Livro> getLivrosByAutor(String nome);

    @Query("SELECT livros FROM Livro livros WHERE LOWER(livros.editora.nome) = (:nome)")
    List<Livro> getLivrosByEditora(String nome);

    @Query("SELECT livros FROM Livro livros WHERE LOWER(livros.categoria.nome) = (:nome)")
    List<Livro> getLivrosByCategoria(String nome);

}
