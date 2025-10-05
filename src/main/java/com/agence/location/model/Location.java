package com.agence.location.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
public class Location implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "client_cin", nullable = false)
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "voiture_immat", nullable = false)
    private Voiture voiture;
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;
    
    @Column(name = "nombre_jours", nullable = false)
    private int nombreJours;
    
    @Column(name = "date_retour_prevue", nullable = false)
    private LocalDate dateRetourPrevue;
    
    @Column(name = "date_retour_reelle")
    private LocalDate dateRetourReelle;
    
    @Column(name = "kilometrage_depart")
    private Double kilometrageDepart;
    
    @Column(name = "kilometrage_retour")
    private Double kilometrageRetour;
    
    @Column(name = "montant_location", nullable = false)
    private double montantLocation;
    
    @Column(name = "dommages_constates")
    private boolean dommagesConstates = false;
    
    @Column(name = "montant_dommages")
    private double montantDommages = 0.0;
    
    @Column(name = "montant_total", nullable = false)
    private double montantTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutLocation statut;
    
    @Column(name = "signature_client", columnDefinition = "TEXT")
    private String signatureClient;
    
    @Column(name = "signature_gestionnaire", columnDefinition = "TEXT")
    private String signatureGestionnaire;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    // Constructeurs
    public Location() {
        this.statut = StatutLocation.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
    }
    
    public Location(Client client, Voiture voiture, LocalDate dateDebut, int nombreJours) {
        this.client = client;
        this.voiture = voiture;
        this.dateDebut = dateDebut;
        this.nombreJours = nombreJours;
        this.statut = StatutLocation.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
        calculerDateRetourPrevue();
        calculerMontantLocation();
    }
    
    // Méthodes métier
    public void calculerDateRetourPrevue() {
        this.dateRetourPrevue = this.dateDebut.plusDays(this.nombreJours);
    }
    
    public void calculerMontantLocation() {
        this.montantLocation = this.voiture.getPrixLocationJ() * this.nombreJours;
        calculerMontantTotal();
    }
    
    public void calculerMontantTotal() {
        this.montantTotal = this.montantLocation + this.montantDommages;
    }
    
    public void accepter(Utilisateur gestionnaire, double kmDepart) {
        this.utilisateur = gestionnaire;
        this.kilometrageDepart = kmDepart;
        this.statut = StatutLocation.EN_COURS;
        this.voiture.marquerCommeLouee();
    }
    
    public void refuser() {
        this.statut = StatutLocation.ANNULEE;
    }
    
    public void annulerParClient() {
        this.statut = StatutLocation.ANNULEE_PAR_CLIENT;
        if (this.voiture.getStatut() == StatutVoiture.LOUEE) {
            this.voiture.liberer();
        }
    }
    
    public void terminer(double kmRetour, boolean dommages, double montantDommages) {
        this.dateRetourReelle = LocalDate.now();
        this.kilometrageRetour = kmRetour;
        this.dommagesConstates = dommages;
        this.montantDommages = montantDommages;
        calculerMontantTotal();
        this.statut = StatutLocation.TERMINEE;
        this.voiture.setKilometrage(kmRetour);
        this.voiture.liberer();
    }
    
    public boolean peutEtreAnnulee() {
        if (this.statut != StatutLocation.EN_ATTENTE && this.statut != StatutLocation.EN_COURS) {
            return false;
        }
        LocalDate dateMinimale = LocalDate.now().plusDays(3);
        return this.dateDebut.isAfter(dateMinimale);
    }
    
    // Getters et Setters (tous)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public Voiture getVoiture() {
        return voiture;
    }
    
    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public int getNombreJours() {
        return nombreJours;
    }
    
    public void setNombreJours(int nombreJours) {
        this.nombreJours = nombreJours;
    }
    
    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
    
    public LocalDate getDateRetourReelle() {
        return dateRetourReelle;
    }
    
    public void setDateRetourReelle(LocalDate dateRetourReelle) {
        this.dateRetourReelle = dateRetourReelle;
    }
    
    public Double getKilometrageDepart() {
        return kilometrageDepart;
    }
    
    public void setKilometrageDepart(Double kilometrageDepart) {
        this.kilometrageDepart = kilometrageDepart;
    }
    
    public Double getKilometrageRetour() {
        return kilometrageRetour;
    }
    
    public void setKilometrageRetour(Double kilometrageRetour) {
        this.kilometrageRetour = kilometrageRetour;
    }
    
    public double getMontantLocation() {
        return montantLocation;
    }
    
    public void setMontantLocation(double montantLocation) {
        this.montantLocation = montantLocation;
    }
    
    public boolean isDommagesConstates() {
        return dommagesConstates;
    }
    
    public void setDommagesConstates(boolean dommagesConstates) {
        this.dommagesConstates = dommagesConstates;
    }
    
    public double getMontantDommages() {
        return montantDommages;
    }
    
    public void setMontantDommages(double montantDommages) {
        this.montantDommages = montantDommages;
    }
    
    public double getMontantTotal() {
        return montantTotal;
    }
    
    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }
    
    public StatutLocation getStatut() {
        return statut;
    }
    
    public void setStatut(StatutLocation statut) {
        this.statut = statut;
    }
    
    public String getSignatureClient() {
        return signatureClient;
    }
    
    public void setSignatureClient(String signatureClient) {
        this.signatureClient = signatureClient;
    }
    
    public String getSignatureGestionnaire() {
        return signatureGestionnaire;
    }
    
    public void setSignatureGestionnaire(String signatureGestionnaire) {
        this.signatureGestionnaire = signatureGestionnaire;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}