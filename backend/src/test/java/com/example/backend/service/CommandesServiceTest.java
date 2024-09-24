package com.example.backend.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.backend.model.Commandes;
import com.example.backend.model.CommandesOffres;
import com.example.backend.model.GenerateurCle;
import com.example.backend.model.Offres;
import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.CommandesOffresRepository;
import com.example.backend.repository.CommandesRepository;
import com.example.backend.repository.OffresRepository;
import com.example.backend.repository.UtilisateursRepository;

import java.util.*;

class CommandesServiceTest {

    @Mock
    private UtilisateursRepository utilisateursRepository;

    @Mock
    private OffresRepository offresRepository;

    @Mock
    private CommandesRepository commandesRepository;

    @Mock
    private CommandesOffresRepository commandesOffresRepository;

    @Mock
    private GenerateurCle generateurCle;

    @InjectMocks
    private CommandesService commandesService;

    private Utilisateurs utilisateur;
    private Offres offre;
    private Commandes commande;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        utilisateur = new Utilisateurs();
        utilisateur.setId(1L);
        
        offre = new Offres();
        offre.setId(1L);
        offre.setPrix(100);
        offre.setStock(10);
        
        commande = new Commandes();
        commande.setId(1L);
    }

    @Test
    void testSauvegarderCommande_Success() throws Exception {
        Long idUtilisateur = 1L;
        List<Map<String, Object>> listeOffres = new ArrayList<>();
        Map<String, Object> offreMap = new HashMap<>();
        offreMap.put("id", offre.getId());
        offreMap.put("quantite", 2);
        listeOffres.add(offreMap);

        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.of(utilisateur));
        when(offresRepository.findById(offre.getId())).thenReturn(Optional.of(offre));
        when(commandesRepository.save(any(Commandes.class))).thenReturn(commande);
        when(commandesOffresRepository.save(any(CommandesOffres.class))).thenReturn(new CommandesOffres());

        Commandes commandeSauvegardee = commandesService.sauvegarderCommande(idUtilisateur, listeOffres);

        // Vérification de la sauvegarde de la commande
        assertNotNull(commandeSauvegardee);
        assertEquals(commande.getId(), commandeSauvegardee.getId());
        assertEquals(100 * 2, commandeSauvegardee.getMontant());
        assertEquals(8, offre.getStock());
        verify(commandesRepository, times(1)).save(any(Commandes.class));
        verify(commandesOffresRepository, times(1)).save(any(CommandesOffres.class));
    }

    @Test
    void testSauvegarderCommande_UtilisateurNonTrouve() {
        Long idUtilisateur = 1L;
        List<Map<String, Object>> listeOffres = new ArrayList<>();

        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.empty());

        // Vérification de l'exception en cas d'utilisateur non trouvé
        Exception exception = assertThrows(Exception.class, () -> {
            commandesService.sauvegarderCommande(idUtilisateur, listeOffres);
        });
        assertEquals("Utilisateur non trouvé avec l'id : " + idUtilisateur, exception.getMessage());
    }

    @Test
    void testSauvegarderCommande_OffreNonTrouvee() {
        Long idUtilisateur = 1L;
        List<Map<String, Object>> listeOffres = new ArrayList<>();
        Map<String, Object> offreMap = new HashMap<>();
        offreMap.put("id", 1L);
        offreMap.put("quantite", 2);
        listeOffres.add(offreMap);

        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.of(utilisateur));
        when(offresRepository.findById(1L)).thenReturn(Optional.empty());

        // Vérification de l'exception en cas d'offre non trouvée
        Exception exception = assertThrows(Exception.class, () -> {
            commandesService.sauvegarderCommande(idUtilisateur, listeOffres);
        });
        assertEquals("Offre non trouvée avec l'id : 1", exception.getMessage());
    }

    @Test
    void testSauvegarderCommande_StockInsuffisant() {
        Long idUtilisateur = 1L;
        List<Map<String, Object>> listeOffres = new ArrayList<>();
        Map<String, Object> offreMap = new HashMap<>();
        offreMap.put("id", offre.getId());
        offreMap.put("quantite", 101);
        listeOffres.add(offreMap);

        when(utilisateursRepository.findById(idUtilisateur)).thenReturn(Optional.of(utilisateur));
        when(offresRepository.findById(offre.getId())).thenReturn(Optional.of(offre));

        // Vérification de l'exception en cas de stock insuffisant
        Exception exception = assertThrows(Exception.class, () -> {
            commandesService.sauvegarderCommande(idUtilisateur, listeOffres);
        });
        assertEquals("Stock insuffisant pour l'offre avec l'id : " + offre.getId(), exception.getMessage());
    }
}