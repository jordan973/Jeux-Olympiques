package com.example.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Paniers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_panier")
	private Long id;
	@Column(name = "date_creation")
	private Date dateCreation;
	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	private Utilisateurs idUtilisateur;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Utilisateurs getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Utilisateurs idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}	

}
