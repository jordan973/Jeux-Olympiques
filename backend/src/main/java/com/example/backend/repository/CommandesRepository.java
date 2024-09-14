package com.example.backend.repository;

import com.example.backend.model.Commandes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandesRepository extends JpaRepository<Commandes, Long> {
	
	List<Commandes> findByUtilisateurId(Long idUtilisateur);
}