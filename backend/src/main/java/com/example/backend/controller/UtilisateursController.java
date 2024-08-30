package com.example.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backend.model.Utilisateurs;
import com.example.backend.service.UtilisateursService;
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
	public ResponseEntity<Utilisateurs> registerUser(@RequestBody Utilisateurs user) {
	    try {
	    	Utilisateurs newUser = utilisateursService.registerUser(user);
	        return ResponseEntity.ok(newUser);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
}
