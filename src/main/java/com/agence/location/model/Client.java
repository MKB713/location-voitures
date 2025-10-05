package com.agence.location.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
    
    @Id
    @Column(name = "cin", length = 20)
    private String cin;
    
    @Column(name = "permis", nullable = false, length = 50)
    private String permis;
    
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    
    @Column(name = "sexe", nullable = false, length = 1)
    private String sexe; // 'H' ou 'F'
    
    @Column(name = "adresse", length = 200)
    private String adresse;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "signature", columnDefinition = "TEXT")
    private String signature;
    
    @Column(name = "date_inscription")
    private LocalDate dateInscription;
    
    // Constructeurs
    public Client() {
        this.dateInscription = LocalDate.now();
    }
    
    public Client(String cin, String prenom, String nom) {
        this.cin = cin;
        this.prenom = prenom;
        this.nom = nom;
        this.dateInscription = LocalDate.now();
    }
    
    // Getters et Setters
    public String getCin() {
        return cin;
    }
    
    public void setCin(String cin) {
        this.cin = cin;
    }
    
    public String getPermis() {
        return permis;
    }
    
    public void setPermis(String permis) {
        this.permis = permis;
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
    
    public String getSexe() {
        return sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public LocalDate getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }
}