package com.example.backend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "paniers_offres")
public class PaniersOffres {
	
    @EmbeddedId
    private PaniersOffresId id;
    @ManyToOne
    @MapsId("idPanier")
    @JoinColumn(name = "id_panier")
    private Paniers panier;
    @ManyToOne
    @MapsId("idOffre")
    @JoinColumn(name = "id_offre")
    private Offres offre;
    private int quantite;
	
    public PaniersOffres() {}

    public PaniersOffres(Paniers panier, Offres offre, int quantite) {
        this.panier = panier;
        this.offre = offre;
        this.quantite = quantite;
        this.id = new PaniersOffresId(panier.getId(), offre.getId());
    }

    public PaniersOffres(PaniersOffresId id, Paniers panier, Offres offre, int quantite) {
        this.id = id;
        this.panier = panier;
        this.offre = offre;
        this.quantite = quantite;
    }
    
    public PaniersOffresId getId() {
        return id;
    }

    public void setId(PaniersOffresId id) {
        this.id = id;
    }

    public Paniers getPanier() {
        return panier;
    }

    public void setPanier(Paniers panier) {
        this.panier = panier;
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
}
