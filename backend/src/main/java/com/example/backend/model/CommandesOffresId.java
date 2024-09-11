package com.example.backend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommandesOffresId implements Serializable {

    private Long idCommande;
    private Long idOffre;

    public CommandesOffresId() {}

    public CommandesOffresId(Long idCommande, Long idOffre) {
        this.idCommande = idCommande;
        this.idOffre = idOffre;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(Long idOffre) {
        this.idOffre = idOffre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandesOffresId that = (CommandesOffresId) o;
        return Objects.equals(idCommande, that.idCommande) && Objects.equals(idOffre, that.idOffre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, idOffre);
    }
}
