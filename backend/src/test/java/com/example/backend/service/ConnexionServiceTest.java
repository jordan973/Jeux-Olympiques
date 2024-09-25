package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.UtilisateursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class ConnexionServiceTest {

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @InjectMocks
    private UtilisateursService utilisateursService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConnecterUtilisateur_Succes() {
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setEmail("email@example.com");
        utilisateur.setMotDePasse("password123");

        Utilisateurs utilisateurExistant = new Utilisateurs();
        utilisateurExistant.setEmail("email@example.com");
        utilisateurExistant.setMotDePasse(new BCryptPasswordEncoder().encode("password123")); // Mot de passe hashé
        
        when(utilisateursRepository.findByEmail(utilisateur.getEmail())).thenReturn(Optional.of(utilisateurExistant));

        ResponseEntity<Map<String, Object>> response = utilisateursService.connecterUtilisateur(utilisateur);

        // Vérifie que la connexion a réussi
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(utilisateurExistant.getEmail(), response.getBody().get("email"));
    }

    @Test
    public void testConnecterUtilisateur_MotDePasseIncorrect() {
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setEmail("email@example.com");
        utilisateur.setMotDePasse("wrongpassword");

        Utilisateurs utilisateurExistant = new Utilisateurs();
        utilisateurExistant.setEmail("email@example.com");
        utilisateurExistant.setMotDePasse(new BCryptPasswordEncoder().encode("password123")); // Mot de passe correct
        
        when(utilisateursRepository.findByEmail(utilisateur.getEmail())).thenReturn(Optional.of(utilisateurExistant));

        ResponseEntity<Map<String, Object>> response = utilisateursService.connecterUtilisateur(utilisateur);

        // Vérifie que la connexion échoue avec un mauvais mot de passe
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testConnecterUtilisateur_UtilisateurNonExistant() {
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setEmail("email@example.com");
        utilisateur.setMotDePasse("password123");

        when(utilisateursRepository.findByEmail(utilisateur.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = utilisateursService.connecterUtilisateur(utilisateur);

        // Vérifie que la connexion échoue si l'utilisateur n'existe pas
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }
}