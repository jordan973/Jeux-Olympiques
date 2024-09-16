package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.model.Offres;
import com.example.backend.service.OffresService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offres")
@CrossOrigin(origins = "http://localhost:3000")
public class OffresController {

    @Autowired
    private OffresService offresService;

    // Récupérer toutes les offres
    @GetMapping
    public List<Offres> getOffres() {
        return offresService.getOffres();
    }

    // Ajouter une nouvelle offre
    @PostMapping
    public ResponseEntity<Offres> ajouterOffre(@RequestBody Offres offre, @RequestParam Long idUtilisateur) {
        if (!offresService.isAdmin(idUtilisateur)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Offres newOffre = offresService.sauvegarderOffre(offre);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOffre);
    }

    // Modifier une offre 
    @PutMapping("/{idOffre}")
    public ResponseEntity<Offres> modifierOffre(@PathVariable Long idOffre, @RequestBody Offres offre, @RequestParam Long idUtilisateur) {
        if (!offresService.isAdmin(idUtilisateur)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Optional<Offres> existingOffre = offresService.getOffreById(idOffre);
        if (existingOffre.isPresent()) {
            offre.setId(idOffre);
            Offres updatedOffre = offresService.sauvegarderOffre(offre);
            return ResponseEntity.ok(updatedOffre);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Supprimer une offre
    @DeleteMapping("/{idOffre}")
    public ResponseEntity<Void> supprimerOffre(@PathVariable Long idOffre, @RequestParam Long idUtilisateur) {
        if (!offresService.isAdmin(idUtilisateur)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        offresService.supprimerOffre(idOffre);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
