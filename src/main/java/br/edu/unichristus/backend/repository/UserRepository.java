package br.edu.unichristus.backend.repository;

import br.edu.unichristus.backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
