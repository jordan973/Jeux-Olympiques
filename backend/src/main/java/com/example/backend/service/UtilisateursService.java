package com.example.backend.service;

import com.example.backend.model.Utilisateurs;
import com.example.backend.model.GenerateurCle;
import com.example.backend.repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateursService {
	@Autowired
    private UtilisateursRepository utilisateursRepository;
	
	@Autowired
	private GenerateurCle generateurCle;

    public Utilisateurs registerUser(Utilisateurs utilisateur) throws Exception {

        Optional<Utilisateurs> existingUser = utilisateursRepository.findByEmail(utilisateur.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("L'adresse email est déjà inscrite.");
        }
        utilisateur.setRole("Utilisateur");
        String cleInscription = generateurCle.genererCle();
        utilisateur.setCleInscription(cleInscription);
        
        return utilisateursRepository.save(utilisateur);
    }
}