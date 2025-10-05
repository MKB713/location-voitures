package com.agence.location.dao;

import com.agence.location.model.Location;
import com.agence.location.model.StatutLocation;
import jakarta.persistence.*;
import java.util.List;

public class LocationDAO implements GenericDAO<Location, Long> {
    
    private EntityManagerFactory emf;
    
    public LocationDAO() {
        this.emf = Persistence.createEntityManagerFactory("LocationPU");
    }
    
    @Override
    public void save(Location location) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(location);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(Location location) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(location);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(Location location) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Location l = em.find(Location.class, location.getId());
            if (l != null) {
                em.remove(l);
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
    public Location findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Location> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Location l ORDER BY l.dateCreation DESC", Location.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Méthodes spécifiques
    public List<Location> findByStatut(StatutLocation statut) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Location l WHERE l.statut = :statut ORDER BY l.dateCreation DESC", Location.class)
                    .setParameter("statut", statut)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Location> findByClient(String cin) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Location l WHERE l.client.cin = :cin ORDER BY l.dateCreation DESC", Location.class)
                    .setParameter("cin", cin)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Location> findEnAttente() {
        return findByStatut(StatutLocation.EN_ATTENTE);
    }
    
    public List<Location> findEnCours() {
        return findByStatut(StatutLocation.EN_COURS);
    }
    
    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}