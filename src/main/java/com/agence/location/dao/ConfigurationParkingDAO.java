package com.agence.location.dao;

import com.agence.location.model.ConfigurationParking;
import jakarta.persistence.*;
import java.util.List;

public class ConfigurationParkingDAO implements GenericDAO<ConfigurationParking, String> {
    
    private EntityManagerFactory emf;
    
    public ConfigurationParkingDAO() {
        this.emf = Persistence.createEntityManagerFactory("LocationPU");
    }
    
    @Override
    public void save(ConfigurationParking config) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(config);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(ConfigurationParking config) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(config);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(ConfigurationParking config) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ConfigurationParking c = em.find(ConfigurationParking.class, config.getRegion());
            if (c != null) {
                em.remove(c);
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
    public ConfigurationParking findById(String region) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ConfigurationParking.class, region);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<ConfigurationParking> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM ConfigurationParking c", ConfigurationParking.class)
                    .getResultList();
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