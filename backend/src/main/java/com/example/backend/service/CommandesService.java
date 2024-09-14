package com.example.backend.service;

import com.example.backend.model.Commandes;
import com.example.backend.model.CommandesOffres;
import com.example.backend.model.CommandesOffresId;
import com.example.backend.model.GenerateurCle;
import com.example.backend.model.Offres;
import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.CommandesOffresRepository;
import com.example.backend.repository.CommandesRepository;
import com.example.backend.repository.OffresRepository;
import com.example.backend.repository.UtilisateursRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommandesService {

    @Autowired
    private CommandesRepository commandesRepository;

    @Autowired
    private CommandesOffresRepository commandesOffresRepository;

    @Autowired
    private OffresRepository offresRepository;
    
    @Autowired
    private UtilisateursRepository utilisateursRepository;
    
	@Autowired
	private GenerateurCle generateurCle;

    @Transactional
    public Commandes sauvegarderCommande(Long idUtilisateur, List<Map<String, Object>> listeOffres) throws Exception {

        // On vérifie l'existence de l'utilisateur puis on l'associe à la commande
        Utilisateurs utilisateur = utilisateursRepository.findById(idUtilisateur)
            .orElseThrow(() -> new Exception("Utilisateur non trouvé avec l'id : " + idUtilisateur));

        Commandes commande = new Commandes();
        commande.setUtilisateur(utilisateur);
        
        commande.setDate(new Date());

        // Calculer le montant total de la commande à partir des offres
        int montantTotal = 0;
        for (Map<String, Object> offre : listeOffres) {
            Long idOffre = ((Number) offre.get("id")).longValue();
            int quantite = (int) offre.get("quantite");

            // Vérifier l'existence de l'offre et récupérer le prix
            Offres offreExistante = offresRepository.findById(idOffre)
                .orElseThrow(() -> new Exception("Offre non trouvée avec l'id : " + idOffre));

            double prix = offreExistante.getPrix();
            montantTotal += prix * quantite;
        }
        commande.setMontant(montantTotal);

        Commandes commandeSauvegardee = commandesRepository.save(commande);

        // Pour chaque offre et quantité reçue, on crée une entrée dans CommandesOffres et on met à jour le stock
        for (Map<String, Object> offre : listeOffres) {
            Long idOffre = ((Number) offre.get("id")).longValue();
            int quantite = (int) offre.get("quantite");

            // On vérifie l'existence de l'offre
            Offres offreExistante = offresRepository.findById(idOffre)
                .orElseThrow(() -> new Exception("Offre non trouvée avec l'id : " + idOffre));
            
            // On vérifie le stock de l'offre
            if (offreExistante.getStock() < quantite) {
                throw new Exception("Stock insuffisant pour l'offre avec l'id : " + idOffre);
            }

            offreExistante.setStock(offreExistante.getStock() - quantite);
            offresRepository.save(offreExistante);
            
            CommandesOffresId commandesOffresId = new CommandesOffresId(commandeSauvegardee.getId(), offreExistante.getId());

            CommandesOffres commandesOffres = new CommandesOffres();
            commandesOffres.setId(commandesOffresId);
            commandesOffres.setCommande(commandeSauvegardee);
            commandesOffres.setOffre(offreExistante);
            commandesOffres.setQuantite(quantite);
            String cleCommande = generateurCle.genererCle();
            commandesOffres.setCleCommande(cleCommande);

            commandesOffresRepository.save(commandesOffres);
        }

        return commandeSauvegardee;
    }
    
    // Fonction pour récupérer une commande avec l'ID commande
    public Optional<Commandes> recupererCommandeParIdCommande(Long idCommande) {
        
    	return commandesRepository.findById(idCommande);
    }
    
    // Fonction pour récupérer une commande avec l'ID commande
    public List<Commandes> recupererCommandeParIdUtilisateur(Long idUtilisateur) {
        
    	return commandesRepository.findByUtilisateurId(idUtilisateur);
    }
}