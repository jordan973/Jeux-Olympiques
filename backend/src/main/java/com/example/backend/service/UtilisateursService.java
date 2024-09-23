package com.example.backend.service;

import com.example.backend.model.Utilisateurs;
import com.example.backend.model.GenerateurCle;
import com.example.backend.repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordHashe = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(passwordHashe);
        
        
        return utilisateursRepository.save(utilisateur);
    }
    
    public ResponseEntity<Map<String, Object>> connecterUtilisateur(Utilisateurs utilisateur) {
        Optional<Utilisateurs> user = utilisateursRepository.findByEmail(utilisateur.getEmail());

        if (user.isPresent()) {
            Utilisateurs utilisateurExistant = user.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (passwordEncoder.matches(utilisateur.getMotDePasse(), utilisateurExistant.getMotDePasse())) {
                UUID token = UUID.randomUUID();
                
                utilisateurExistant.setToken(token);
                utilisateurExistant.setCreationSession(new Date());

                utilisateursRepository.save(utilisateurExistant);
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("id", utilisateurExistant.getId());
                response.put("prenom", utilisateurExistant.getPrenom());
                response.put("nom", utilisateurExistant.getNom());
                response.put("email", utilisateurExistant.getEmail());

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    
    public Optional<Utilisateurs> recupererUtilisateurParId(Long id) {
        return utilisateursRepository.findById(id);
    }
}