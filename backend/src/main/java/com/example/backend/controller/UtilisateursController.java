package com.example.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backend.model.Utilisateurs;
import com.example.backend.service.UtilisateursService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateursController {
	
	@Autowired
    private UtilisateursService utilisateursService;
	
	@PostMapping("/inscription")
	public ResponseEntity<Utilisateurs> inscrireUtilisateur(@RequestBody Utilisateurs utilisateur) {
	    
		try {
	    	Utilisateurs nouvelUtilisateur = utilisateursService.inscrireUtilisateur(utilisateur);
	        return ResponseEntity.ok(nouvelUtilisateur);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	
    @PostMapping("/connexion")
    public ResponseEntity<?> loginUtilisateur(@RequestBody Utilisateurs utilisateur) { 
        try {
            ResponseEntity<Map<String, Object>> response = utilisateursService.connecterUtilisateur(utilisateur);
            if (response != null) {
                return response;
            } else {
                return ResponseEntity.status(401).body("Email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }
}
