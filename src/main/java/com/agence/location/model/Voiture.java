package com.agence.location.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "voitures")
public class Voiture implements Serializable {
    
    @Id
    @Column(name = "immatriculation", length = 20)
    private String immatriculation;
    
    @Column(name = "nb_places", nullable = false)
    private int nbPlaces;
    
    @Column(name = "marque", nullable = false, length = 50)
    private String marque;
    
    @Column(name = "modele", nullable = false, length = 50)
    private String modele;
    
    @Column(name = "date_mise_circulation", nullable = false)
    private LocalDate dateMiseCirculation;
    
    @Column(name = "kilometrage", nullable = false)
    private double kilometrage;
    
    @Column(name = "type_carburant", nullable = false, length = 20)
    private String typeCarburant;
    
    @Column(name = "categorie", nullable = false, length = 30)
    private String categorie;
    
    @Column(name = "prix_location_j", nullable = false)
    private double prixLocationJ;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutVoiture statut;
    
    @Column(name = "region", nullable = false, length = 50)
    private String region;
    
    @Column(name = "photo_url", length = 255)
    private String photoUrl;
    
    // Constructeurs
    public Voiture() {
        this.statut = StatutVoiture.DISPONIBLE;
    }
    
    public Voiture(String immatriculation, String marque, String modele) {
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.statut = StatutVoiture.DISPONIBLE;
    }
    
    // Méthodes métier
    public boolean isDisponible() {
        return this.statut == StatutVoiture.DISPONIBLE;
    }
    
    public void marquerCommeLouee() {
        this.statut = StatutVoiture.LOUEE;
    }
    
    public void liberer() {
        this.statut = StatutVoiture.DISPONIBLE;
    }
    
    // Getters et Setters
    public String getImmatriculation() {
        return immatriculation;
    }
    
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }
    
    public int getNbPlaces() {
        return nbPlaces;
    }
    
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }
    
    public String getMarque() {
        return marque;
    }
    
    public void setMarque(String marque) {
        this.marque = marque;
    }
    
    public String getModele() {
        return modele;
    }
    
    public void setModele(String modele) {
        this.modele = modele;
    }
    
    public LocalDate getDateMiseCirculation() {
        return dateMiseCirculation;
    }
    
    public void setDateMiseCirculation(LocalDate dateMiseCirculation) {
        this.dateMiseCirculation = dateMiseCirculation;
    }
    
    public double getKilometrage() {
        return kilometrage;
    }
    
    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }
    
    public String getTypeCarburant() {
        return typeCarburant;
    }
    
    public void setTypeCarburant(String typeCarburant) {
        this.typeCarburant = typeCarburant;
    }
    
    public String getCategorie() {
        return categorie;
    }
    
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    public double getPrixLocationJ() {
        return prixLocationJ;
    }
    
    public void setPrixLocationJ(double prixLocationJ) {
        this.prixLocationJ = prixLocationJ;
    }
    
    public StatutVoiture getStatut() {
        return statut;
    }
    
    public void setStatut(StatutVoiture statut) {
        this.statut = statut;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}