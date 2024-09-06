package com.example.backend.repository;

import com.example.backend.model.Paniers;
import com.example.backend.model.Utilisateurs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaniersRepository extends JpaRepository<Paniers, Long> {
	
    Optional<Paniers> findByIdUtilisateur(Utilisateurs idUtilisateur);
}
