package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
public class Utilisateurs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id_utilisateur")
	private Long id;
	private String prenom;
	private String nom;
	private String email;
	private String role;
	@Column(name = "mot_de_passe")
	private String motDePasse;
	@Column(name = "cle_inscription")
    private String cleInscription;
	private String token;
	@Column(name = "creation_session")
	private Date creationSession;
	@JsonIgnore
	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Commandes> commandes;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getCleInscription() {
		return cleInscription;
	}
	
	public void setCleInscription(String cleInscription) {
		this.cleInscription = cleInscription;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Date getCreationSession() {
		return creationSession;
	}

	public void setCreationSession(Date creationSession) {
		this.creationSession = creationSession;
	}
	
    public Set<Commandes> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commandes> commandes) {
        this.commandes = commandes;
    }
}
