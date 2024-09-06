package com.example.backend.repository;

import com.example.backend.model.Offres;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OffresRepository extends JpaRepository<Offres, Long> {
    
	Optional<Offres> findById(Long id);
}
