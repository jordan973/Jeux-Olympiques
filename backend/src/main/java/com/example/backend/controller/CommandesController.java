package com.example.backend.controller;

import com.example.backend.model.Commandes;
import com.example.backend.service.CommandesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:3000")
public class CommandesController {

    @Autowired
    private CommandesService commandesService;

    @PostMapping
    public ResponseEntity<Commandes> creerCommande(@RequestParam Long idUtilisateur, @RequestBody List<Map<String, Object>> listeOffres) {
        try {
            Commandes commandeSauvegardee = commandesService.sauvegarderCommande(idUtilisateur, listeOffres);       
            return ResponseEntity.ok(commandeSauvegardee);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
