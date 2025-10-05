package com.agence.location.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    
    @Column(name = "date_recrutement", nullable = false)
    private LocalDate dateRecrutement;
    
    @Column(name = "adresse", length = 200)
    private String adresse;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @Column(name = "signature", columnDefinition = "TEXT")
    private String signature;
    
    // Constructeurs
    public Utilisateur() {}
    
    public Utilisateur(String username, String prenom, String nom, Role role) {
        this.username = username;
        this.prenom = prenom;
        this.nom = nom;
        this.role = role;
        this.dateRecrutement = LocalDate.now();
    }
    
    // Méthodes métier
    public boolean isChefAgence() {
        return this.role == Role.CHEF_AGENCE;
    }
    
    public boolean isGestionnaire() {
        return this.role == Role.GESTIONNAIRE;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public LocalDate getDateRecrutement() {
        return dateRecrutement;
    }
    
    public void setDateRecrutement(LocalDate dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
}