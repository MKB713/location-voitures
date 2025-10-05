package com.agence.location.dao;

import com.agence.location.model.Voiture;
import com.agence.location.model.StatutVoiture;
import jakarta.persistence.*;
import java.util.List;

public class VoitureDAO implements GenericDAO<Voiture, String> {
    
    private EntityManagerFactory emf;
    
    public VoitureDAO() {
        this.emf = Persistence.createEntityManagerFactory("LocationPU");
    }
    
    @Override
    public void save(Voiture voiture) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(voiture);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(Voiture voiture) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(voiture);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(Voiture voiture) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Voiture v = em.find(Voiture.class, voiture.getImmatriculation());
            if (v != null) {
                em.remove(v);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Voiture findById(String immatriculation) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Voiture.class, immatriculation);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Voiture> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM Voiture v", Voiture.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Méthodes spécifiques
    public List<Voiture> findByStatut(StatutVoiture statut) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM Voiture v WHERE v.statut = :statut", Voiture.class)
                    .setParameter("statut", statut)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Voiture> findDisponibles() {
        return findByStatut(StatutVoiture.DISPONIBLE);
    }
    
    // NOUVEAU : Compter voitures par région
    public long countByRegion(String region) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(v) FROM Voiture v WHERE v.region = :region", Long.class)
                    .setParameter("region", region)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
    
    // Recherche multicritères
    public List<Voiture> searchVoitures(String region, String marque, String categorie, Integer nbPlaces) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT v FROM Voiture v WHERE v.statut = :statut");
            
            if (region != null && !region.isEmpty()) {
                jpql.append(" AND v.region = :region");
            }
            if (marque != null && !marque.isEmpty()) {
                jpql.append(" AND v.marque = :marque");
            }
            if (categorie != null && !categorie.isEmpty()) {
                jpql.append(" AND v.categorie = :categorie");
            }
            if (nbPlaces != null) {
                jpql.append(" AND v.nbPlaces >= :nbPlaces");
            }
            
            TypedQuery<Voiture> query = em.createQuery(jpql.toString(), Voiture.class);
            query.setParameter("statut", StatutVoiture.DISPONIBLE);
            
            if (region != null && !region.isEmpty()) {
                query.setParameter("region", region);
            }
            if (marque != null && !marque.isEmpty()) {
                query.setParameter("marque", marque);
            }
            if (categorie != null && !categorie.isEmpty()) {
                query.setParameter("categorie", categorie);
            }
            if (nbPlaces != null) {
                query.setParameter("nbPlaces", nbPlaces);
            }
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}