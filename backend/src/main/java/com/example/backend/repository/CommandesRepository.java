package com.example.backend.repository;

import com.example.backend.model.Commandes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandesRepository extends JpaRepository<Commandes, Long> {
	
}
