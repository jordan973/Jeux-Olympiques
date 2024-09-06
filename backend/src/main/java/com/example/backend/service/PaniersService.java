package com.example.backend.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.Offres;
import com.example.backend.model.Paniers;
import com.example.backend.model.PaniersOffres;
import com.example.backend.model.PaniersOffresId;
import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.OffresRepository;
import com.example.backend.repository.PaniersOffresRepository;
import com.example.backend.repository.PaniersRepository;
import com.example.backend.repository.UtilisateursRepository;

@Service
public class PaniersService {
	
	@Autowired
	private UtilisateursRepository utilisateursRepository;

    @Autowired
    private PaniersRepository panierRepository;

    @Autowired
    private OffresRepository offreRepository;
    
    @Autowired
    private PaniersOffresRepository paniersOffresRepository;
    
    public String ajouterAuPanier(Long idUtilisateur, Long idOffre) {
        
        // Récupérer l'objet Utilisateurs à partir de l'ID
        Utilisateurs utilisateur = utilisateursRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
    	// Recherche d'un panier existant + création s'il n'existe pas
    	Optional<Paniers> panierOptional = panierRepository.findByIdUtilisateur(utilisateur);
    	Paniers panier;
    	if (panierOptional.isPresent()) {
    	    panier = panierOptional.get();
    	} else {
    	    Paniers nouveauPanier = new Paniers();
    	    nouveauPanier.setIdUtilisateur(utilisateur);
    	    nouveauPanier.setDateCreation(new Date());
    	    panier = panierRepository.save(nouveauPanier);
    	}

    	// Recherche de l'offre + Erreur si elle n'existe pas
        Optional<Offres> offreOptional = offreRepository.findById(idOffre);
        Offres offre;
        if (offreOptional.isPresent()) {
        	offre = offreOptional.get();
        } else {
        	throw new RuntimeException("Offre introuvable");
        }
        
        PaniersOffresId paniersOffresId = new PaniersOffresId(panier.getId(), offre.getId());
        Optional<PaniersOffres> paniersOffresOptional = paniersOffresRepository.findById(paniersOffresId);

        if (paniersOffresOptional.isPresent()) {
            PaniersOffres paniersOffres = paniersOffresOptional.get();
            paniersOffres.setQuantite(paniersOffres.getQuantite() + 1);
            paniersOffresRepository.save(paniersOffres);
        } else {
            PaniersOffres paniersOffres = new PaniersOffres(panier, offre, 1);
            paniersOffresRepository.save(paniersOffres);
        }
        
        return "Offre ajoutée au panier.";
    }
    
    public String supprimerDuPanier(Long idUtilisateur, Long idOffre) {
    	
        // Récupérer l'objet Utilisateurs à partir de l'ID
        Utilisateurs utilisateur = utilisateursRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    	
    	// Recherche d'un panier existant + création s'il n'existe pas
    	Optional<Paniers> panierOptional = panierRepository.findByIdUtilisateur(utilisateur);
    	Paniers panier;
    	if (panierOptional.isPresent()) {
    	    panier = panierOptional.get();
    	} else {
        	throw new RuntimeException("Panier introuvable");
        }
    	
    	// Recherche de l'offre dans le panier    	
    	PaniersOffresId paniersOffresId = new PaniersOffresId(panier.getId(), idOffre);
        Optional<PaniersOffres> paniersOffresOptional = paniersOffresRepository.findById(paniersOffresId);
        
        if (paniersOffresOptional.isPresent()) {
            PaniersOffres paniersOffres = paniersOffresOptional.get();
            paniersOffres.setQuantite(paniersOffres.getQuantite() - 1);
            paniersOffresRepository.save(paniersOffres);
            if (paniersOffres.getQuantite() == 0) {
            	paniersOffresRepository.delete(paniersOffres);
            }
        } else {
        	throw new RuntimeException("Cette offre n'est pas dans le panier.");
        }
    	
    	return "Offre supprimée du panier.";
    }
}
