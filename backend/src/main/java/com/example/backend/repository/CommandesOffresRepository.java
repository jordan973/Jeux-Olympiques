package com.example.backend.repository;

import com.example.backend.model.CommandesOffres;
import com.example.backend.model.CommandesOffresId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandesOffresRepository extends JpaRepository<CommandesOffres, CommandesOffresId> {
	
	List<CommandesOffres> findByCommandeId(Long commandeId);
}
