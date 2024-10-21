package com.example.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backend.model.Utilisateurs;
import com.example.backend.security.ValidationToken;
import com.example.backend.service.UtilisateursService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateursController {
	
	@Autowired
    private UtilisateursService utilisateursService;
	
	@Autowired
    private ValidationToken validationToken;
	
	@PostMapping("/inscription")
	public ResponseEntity<Map<String, String>> inscrireUtilisateur(@RequestBody Utilisateurs utilisateur) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        Utilisateurs nouvelUtilisateur = utilisateursService.inscrireUtilisateur(utilisateur);
	        return ResponseEntity.ok().body(Collections.singletonMap("message", "Inscription réussie"));
	    } catch (Exception e) {
	        response.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(response);
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
    
    @GetMapping("/profile/{idUtilisateur}")
    public ResponseEntity<Utilisateurs> getUserProfile(@PathVariable Long idUtilisateur, @RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletResponse response) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);

        try {
            UUID uuidToken = UUID.fromString(token);
            if (!validationToken.verifierTokenDansBdd(uuidToken)) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
        }

        Optional<Utilisateurs> utilisateur = utilisateursService.recupererUtilisateurParId(idUtilisateur);
        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}