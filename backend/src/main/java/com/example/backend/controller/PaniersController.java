package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.service.PaniersService;

@RestController
@RequestMapping("/api/panier")
@CrossOrigin(origins = "http://localhost:3000")
public class PaniersController {

	@Autowired
	PaniersService paniersService;
	
	@PostMapping("/ajouter")
	public ResponseEntity<String> ajouterOffrePanier(@RequestParam Long idUtilisateur, @RequestParam Long idOffre) {
		try {
			String message = paniersService.ajouterAuPanier(idUtilisateur, idOffre);
			return ResponseEntity.ok(message);
		} catch (Exception e){
			return ResponseEntity.status(500).body("Erreur lors de l'ajout au panier : " + e.getMessage());
		}
	}
	
	@PostMapping("/supprimer")
	public ResponseEntity<String> supprimerOffrePanier(@RequestParam Long idUtilisateur, @RequestParam Long idOffre){
		try {
			String message = paniersService.supprimerDuPanier(idUtilisateur, idOffre);
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erreur lors de la suppression de l'offre : " + e.getMessage());
		}
	}
}
