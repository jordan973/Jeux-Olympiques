package com.example.backend.service;

import com.example.backend.model.Utilisateurs;
import com.example.backend.model.GenerateurCle;
import com.example.backend.repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateursService {
	@Autowired
    private UtilisateursRepository utilisateursRepository;
	
	@Autowired
	private GenerateurCle generateurCle;

    public Utilisateurs inscrireUtilisateur(Utilisateurs utilisateur) throws Exception {

        Optional<Utilisateurs> existingUser = utilisateursRepository.findByEmail(utilisateur.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("L'adresse email est déjà inscrite.");
        }
        
        utilisateur.setRole("Utilisateur");
        String cleInscription = generateurCle.genererCle();
        utilisateur.setCleInscription(cleInscription);
        
        return utilisateursRepository.save(utilisateur);
    }
    
    public String connecterUtilisateur(Utilisateurs utilisateur) {

        Optional<Utilisateurs> user = utilisateursRepository.findByEmail(utilisateur.getEmail());
        
        if (user.isPresent() && user.get().getMotDePasse().equals(utilisateur.getMotDePasse())) {
            String token = UUID.randomUUID().toString();
            
            Utilisateurs utilisateurExistant = user.get();
            utilisateurExistant.setToken(token);  
            utilisateurExistant.setCreationSession(new Date());

            utilisateursRepository.save(utilisateurExistant);

            return token;
        }
        
        return null;
    }
}