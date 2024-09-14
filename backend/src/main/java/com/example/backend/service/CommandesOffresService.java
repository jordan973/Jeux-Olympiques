package com.example.backend.service;

import com.example.backend.model.CommandesOffres;
import com.example.backend.repository.CommandesOffresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandesOffresService {

    @Autowired
    private CommandesOffresRepository commandesOffresRepository;

    public List<CommandesOffres> recupererCommandesOffres(Long idCommande) {
        
    	return commandesOffresRepository.findByCommandeId(idCommande);
    }
}
