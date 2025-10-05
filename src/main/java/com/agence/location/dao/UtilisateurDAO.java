package com.agence.location.dao;

import com.agence.location.model.Utilisateur;
import com.agence.location.model.Role;
import jakarta.persistence.*;
import java.util.List;

public class UtilisateurDAO implements GenericDAO<Utilisateur, Long> {
    
    private EntityManagerFactory emf;
    
    public UtilisateurDAO() {
        this.emf = Persistence.createEntityManagerFactory("LocationPU");
    }
    
    @Override
    public void save(Utilisateur utilisateur) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(Utilisateur utilisateur) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(Utilisateur utilisateur) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Utilisateur u = em.find(Utilisateur.class, utilisateur.getId());
            if (u != null) {
                em.remove(u);
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
    public Utilisateur findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Utilisateur.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Utilisateur> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Méthodes spécifiques
    public Utilisateur findByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username", Utilisateur.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Utilisateur authenticate(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username AND u.password = :password", Utilisateur.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<Utilisateur> findByRole(Role role) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.role = :role", Utilisateur.class)
                    .setParameter("role", role)
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