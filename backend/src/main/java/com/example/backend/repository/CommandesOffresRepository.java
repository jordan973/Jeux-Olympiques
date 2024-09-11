package com.example.backend.repository;

import com.example.backend.model.CommandesOffres;
import com.example.backend.model.CommandesOffresId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandesOffresRepository extends JpaRepository<CommandesOffres, CommandesOffresId> {
	
}
