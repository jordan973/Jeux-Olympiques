package com.example.backend.controller;

import com.example.backend.model.CommandesOffres;
import com.example.backend.security.ValidationToken;
import com.example.backend.service.CommandesOffresService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/commandes-offres")
@CrossOrigin(origins = "http://localhost:3000")
public class CommandesOffresController {

    @Autowired
    private CommandesOffresService commandesOffresService;
    
    @Autowired
    private ValidationToken validationToken;

    @GetMapping("/commande/{idCommande}")
    public ResponseEntity<List<CommandesOffres>> recupererCommandesOffres(@PathVariable Long idCommande, @RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletResponse response) {
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

        List<CommandesOffres> offres = commandesOffresService.recupererCommandesOffres(idCommande);
        if (offres.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(offres);
        }
    }
}