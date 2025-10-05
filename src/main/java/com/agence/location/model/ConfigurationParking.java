package com.agence.location.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "configuration_parking")
public class ConfigurationParking implements Serializable {
    
    @Id
    @Column(name = "region", length = 50)
    private String region;
    
    @Column(name = "capacite_max", nullable = false)
    private int capaciteMax;
    
    @Column(name = "nom_parking", nullable = false, length = 100)
    private String nomParking;
    
    @Column(name = "adresse", length = 200)
    private String adresse;
    
    // Constructeurs
    public ConfigurationParking() {}
    
    public ConfigurationParking(String region, int capaciteMax, String nomParking) {
        this.region = region;
        this.capaciteMax = capaciteMax;
        this.nomParking = nomParking;
    }
    
    // Méthodes métier
    public boolean isFull(int nbVoitures) {
        return nbVoitures >= capaciteMax;
    }
    
    public double getTauxOccupation(int nbVoitures) {
        return (double) nbVoitures / capaciteMax * 100;
    }
    
    public boolean isNearCapacity(int nbVoitures) {
        return getTauxOccupation(nbVoitures) >= 90;
    }
    
    // Getters et Setters
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public int getCapaciteMax() {
        return capaciteMax;
    }
    
    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }
    
    public String getNomParking() {
        return nomParking;
    }
    
    public void setNomParking(String nomParking) {
        this.nomParking = nomParking;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}