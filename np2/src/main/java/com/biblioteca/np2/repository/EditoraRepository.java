package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EditoraRepository extends JpaRepository<Editora, Integer> {
    @Query("SELECT editora FROM Editora editora WHERE LOWER(editora.nome) = LOWER(:nome)")
    Optional<Editora> findByNomeIgnoreCases(String nome);
}
