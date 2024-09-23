package com.example.backend.controller;

import com.example.backend.model.Commandes;
import com.example.backend.security.ValidationToken;
import com.example.backend.service.CommandesService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:3000")
public class CommandesController {

    @Autowired
    private CommandesService commandesService;
    
    @Autowired
    private ValidationToken validationToken;

    // Endpoint pour le paiement
    @PostMapping
    public ResponseEntity<Commandes> creerCommande(@RequestParam Long idUtilisateur, @RequestBody List<Map<String, Object>> listeOffres, @RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletResponse response) {
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

        try {
            Commandes commandeSauvegardee = commandesService.sauvegarderCommande(idUtilisateur, listeOffres);       
            return ResponseEntity.ok(commandeSauvegardee);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    
    // Endpoint pour la confirmation de commande
    @GetMapping("/{idCommande}")
    public ResponseEntity<Commandes> recupererCommandeParIdCommande(@PathVariable Long idCommande, @RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletResponse response) {
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

        Optional<Commandes> commande = commandesService.recupererCommandeParIdCommande(idCommande);
        if (commande.isPresent()) {
            return ResponseEntity.ok(commande.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    
    // Endpoint pour la confirmation de commande
    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Commandes>> recupererCommandeParIdUtilisateur(@PathVariable Long idUtilisateur, @RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletResponse response) {
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

        List<Commandes> commandes = commandesService.recupererCommandeParIdUtilisateur(idUtilisateur);
        if (!commandes.isEmpty()) {
            return ResponseEntity.ok(commandes);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}