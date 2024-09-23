package com.example.backend.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.UtilisateursRepository;

@Component
public class ValidationToken {

    @Autowired
    private UtilisateursRepository utilisateurRepository;

    public boolean verifierTokenDansBdd(UUID token) {
        Utilisateurs utilisateur = utilisateurRepository.findByToken(token);
        return utilisateur != null;
    }
}