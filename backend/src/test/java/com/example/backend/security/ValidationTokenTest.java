package com.example.backend.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.UtilisateursRepository;

public class ValidationTokenTest {

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @InjectMocks
    private ValidationToken validationToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifierTokenDansBdd_TokenPresent() {
        UUID token = UUID.randomUUID();
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setToken(token);
        when(utilisateursRepository.findByToken(token)).thenReturn(utilisateur);

        // Vérification si le token est présent
        boolean result = validationToken.verifierTokenDansBdd(token);
        assertTrue(result);
    }

    @Test
    void testVerifierTokenDansBdd_TokenAbsent() {
        UUID token = UUID.randomUUID();
        when(utilisateursRepository.findByToken(token)).thenReturn(null);

        // Vérification de l'erreur si le token est absent
        boolean result = validationToken.verifierTokenDansBdd(token);
        assertFalse(result);
    }
}