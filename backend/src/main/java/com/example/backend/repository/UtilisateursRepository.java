package com.example.backend.repository;

import com.example.backend.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {

    Optional<Utilisateurs> findByEmail(String email);
    Utilisateurs findByToken(UUID token);
}