package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("SELECT categoria FROM Categoria categoria WHERE LOWER(categoria.nome) = LOWER(:nome)")
    Optional<Categoria> findByNomeIgnoreCases(String nome);
}

