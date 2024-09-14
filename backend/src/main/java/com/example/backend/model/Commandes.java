package com.example.backend.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Commandes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id_commande")
	private Long id;
	private int montant;
	private Date date;
	@ManyToOne
	@JoinColumn(name = "id_utilisateur") 
	private Utilisateurs utilisateur;
	@JsonManagedReference
	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandesOffres> commandesOffres;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public List<CommandesOffres> getCommandesOffres() {  // Getter pour la liste de CommandesOffres
        return commandesOffres;
    }

    public void setCommandesOffres(List<CommandesOffres> commandesOffres) {  // Setter pour la liste de CommandesOffres
        this.commandesOffres = commandesOffres;
    }
}

