package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.backend.model.GenerateurCle;
import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.UtilisateursRepository;
import com.example.backend.service.UtilisateursService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UtilisateursServiceTest {

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @Mock
    private GenerateurCle generateurCle;

    @InjectMocks
    private UtilisateursService utilisateursService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInscrireUtilisateur_Success() throws Exception {
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setEmail("email@example.com");
        utilisateur.setMotDePasse("password123");

        when(utilisateursRepository.findByEmail(utilisateur.getEmail())).thenReturn(Optional.empty());
        when(generateurCle.genererCle()).thenReturn("clé-générée");
        when(utilisateursRepository.save(any(Utilisateurs.class))).thenReturn(utilisateur);
        
        Utilisateurs resultat = utilisateursService.inscrireUtilisateur(utilisateur);

        // Vérification du rôle
        assertEquals("Utilisateur", resultat.getRole());

        // Vérification du hashage du MDP
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue(passwordEncoder.matches("password123", resultat.getMotDePasse()));

        // Vérification de la sauvegarde de l'utilisateur
        verify(utilisateursRepository, times(1)).save(utilisateur);
    }

    @Test
    public void testInscrireUtilisateur_EmailDejaExistant() {
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setEmail("email@example.com");

        when(utilisateursRepository.findByEmail(utilisateur.getEmail())).thenReturn(Optional.of(utilisateur));

        // Vérification de l'exception
        Exception exception = assertThrows(Exception.class, () -> {
            utilisateursService.inscrireUtilisateur(utilisateur);
        });
        assertEquals("L'adresse email est déjà inscrite.", exception.getMessage());

        // Vérification que la méthode save() n'est pas appelée
        verify(utilisateursRepository, never()).save(any(Utilisateurs.class));
    }
}