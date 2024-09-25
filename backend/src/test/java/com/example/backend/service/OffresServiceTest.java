package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.backend.model.Offres;
import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.OffresRepository;
import com.example.backend.repository.UtilisateursRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OffresServiceTest {

    @Mock
    private OffresRepository offresRepository;

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @InjectMocks
    private OffresService offresService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOffres() {
        Offres offre1 = new Offres();
        offre1.setId(1L);
        offre1.setNom("Offre 1");

        Offres offre2 = new Offres();
        offre2.setId(2L);
        offre2.setNom("Offre 2");

        when(offresRepository.findAll()).thenReturn(Arrays.asList(offre1, offre2));
        List<Offres> offresList = offresService.getOffres();

        // Vérification de la récupération des offres
        assertNotNull(offresList);
        assertEquals(2, offresList.size());
        assertEquals("Offre 1", offresList.get(0).getNom());
        assertEquals("Offre 2", offresList.get(1).getNom());
    }

    @Test
    void testGetOffreById() {
        Long idOffre = 1L;
        Offres offre = new Offres();
        offre.setId(idOffre);
        offre.setNom("Offre 1");

        when(offresRepository.findById(idOffre)).thenReturn(Optional.of(offre));
        Optional<Offres> result = offresService.getOffreById(idOffre);

        // Vérification de la récupération des offres par l'ID
        assertTrue(result.isPresent());
        assertEquals(offre, result.get());
    }

    @Test
    void testGetOffreById_NotFound() {
        Long idOffre = 1L;
        when(offresRepository.findById(idOffre)).thenReturn(Optional.empty());
        Optional<Offres> result = offresService.getOffreById(idOffre);

        // Vérification de l'erreur en cas d'offre introuvable
        assertFalse(result.isPresent());
    }

    @Test
    void testSauvegarderOffre() {
        Offres offre = new Offres();
        offre.setNom("Nouvelle Offre");
        when(offresRepository.save(offre)).thenReturn(offre);
        Offres savedOffre = offresService.sauvegarderOffre(offre);

        // Vérification de la sauvegarde de l'offre
        assertNotNull(savedOffre);
        assertEquals("Nouvelle Offre", savedOffre.getNom());
        verify(offresRepository, times(1)).save(offre);
    }

    @Test
    void testSupprimerOffre() {
        Long idOffre = 1L;
        offresService.supprimerOffre(idOffre);
        
        // Vérification de la suppression de l'offre
        verify(offresRepository, times(1)).deleteById(idOffre);
    }

    @Test
    void testIsAdmin() {
        Long idUtilisateur = 1L;
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setRole("Administrateur");
        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.of(utilisateur));
        boolean result = offresService.isAdmin(idUtilisateur);

        // Vérification du rôle administrateur
        assertTrue(result);
    }

    @Test
    void testIsAdmin_NotAdmin() {
        Long idUtilisateur = 1L;
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setRole("Utilisateur");
        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.of(utilisateur));
        boolean result = offresService.isAdmin(idUtilisateur);

        // Vérification de l'erreur si le rôle n'est pas administrateur
        assertFalse(result);
    }

    @Test
    void testIsAdmin_UtilisateurNonTrouve() {
        Long idUtilisateur = 1L;
        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.empty());
        boolean result = offresService.isAdmin(idUtilisateur);

        // Vérification de l'erreur sir l'utilisateur n'est pas trouvé
        assertFalse(result);
    }
}