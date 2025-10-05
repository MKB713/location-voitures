package com.agence.location.dao;

import com.agence.location.model.Client;
import jakarta.persistence.*;
import java.util.List;

public class ClientDAO implements GenericDAO<Client, String> {
    
    private EntityManagerFactory emf;
    
    public ClientDAO() {
        this.emf = Persistence.createEntityManagerFactory("LocationPU");
    }
    
    @Override
    public void save(Client client) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(client);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(Client client) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(client);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(Client client) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Client c = em.find(Client.class, client.getCin());
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
    public Client findById(String cin) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Client.class, cin);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Client> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Client c", Client.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Méthode spécifique pour recherche par nom
    public List<Client> findByNom(String nom) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.nom LIKE :nom", Client.class)
                    .setParameter("nom", "%" + nom + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Méthode pour authentification
    public Client findByCinAndPassword(String cin, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.cin = :cin AND c.password = :password", Client.class)
                    .setParameter("cin", cin)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
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