package com.example.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PaniersOffresId implements Serializable {

    @Column(name = "id_panier")
    private Long idPanier;
    @Column(name = "id_offre")
    private Long idOffre;

    public PaniersOffresId() {}

    public PaniersOffresId(Long idPanier, Long idOffre) {
        this.idPanier = idPanier;
        this.idOffre = idOffre;
    }

    public Long getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(Long idPanier) {
        this.idPanier = idPanier;
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
        PaniersOffresId that = (PaniersOffresId) o;
        return Objects.equals(idPanier, that.idPanier) && Objects.equals(idOffre, that.idOffre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPanier, idOffre);
    }
}
