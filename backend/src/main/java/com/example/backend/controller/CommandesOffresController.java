package com.example.backend.controller;

import com.example.backend.model.CommandesOffres;
import com.example.backend.service.CommandesOffresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes-offres")
@CrossOrigin(origins = "http://localhost:3000")
public class CommandesOffresController {

    @Autowired
    private CommandesOffresService commandesOffresService;

    @GetMapping("/commande/{idCommande}")
    public ResponseEntity<List<CommandesOffres>> recupererCommandesOffres(@PathVariable Long idCommande) {
        List<CommandesOffres> offres = commandesOffresService.recupererCommandesOffres(idCommande);
        if (offres.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(offres);
        }
    }
}
