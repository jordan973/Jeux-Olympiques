package com.example.backend.repository;

import com.example.backend.model.PaniersOffres;
import com.example.backend.model.PaniersOffresId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaniersOffresRepository extends JpaRepository<PaniersOffres, PaniersOffresId> {
    
}
