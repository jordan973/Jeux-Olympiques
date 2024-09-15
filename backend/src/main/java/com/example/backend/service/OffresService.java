package com.example.backend.service;

import com.example.backend.model.Offres;
import com.example.backend.model.Utilisateurs;
import com.example.backend.repository.OffresRepository;
import com.example.backend.repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffresService {

    @Autowired
    private OffresRepository offresRepository;

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    public List<Offres> getOffres() {
        return offresRepository.findAll();
    }

    public Optional<Offres> getOffreById(Long id) {
        return offresRepository.findById(id);
    }

    public Offres sauvegarderOffre(Offres offre) {
        return offresRepository.save(offre);
    }

    public void supprimerOffre(Long id) {
        offresRepository.deleteById(id);
    }

    public boolean isAdmin(Long idUtilisateur) {
        Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(idUtilisateur);
        return utilisateur.isPresent() && utilisateur.get().getRole().equals("Administrateur");
    }
}