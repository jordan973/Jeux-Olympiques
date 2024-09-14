package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "commandes_offres")
public class CommandesOffres {

    @EmbeddedId
    private CommandesOffresId id;
    @JsonBackReference
    @ManyToOne
    @MapsId("idCommande")
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    @ManyToOne
    @MapsId("idOffre")
    @JoinColumn(name = "id_offre")
    private Offres offre;
    private int quantite;
	@Column(name = "cle_commande")
	private String cleCommande;

    public CommandesOffresId getId() {
        return id;
    }

    public void setId(CommandesOffresId id) {
        this.id = id;
    }

    public Commandes getCommande() {
        return commande;
    }

    public void setCommande(Commandes commande) {
        this.commande = commande;
    }

    public Offres getOffre() {
        return offre;
    }

    public void setOffre(Offres offre) {
        this.offre = offre;
    }

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	public String getCleCommande() {
		return cleCommande;
	}
	
	public void setCleCommande(String cleCommande) {
		this.cleCommande = cleCommande;
	}
}
