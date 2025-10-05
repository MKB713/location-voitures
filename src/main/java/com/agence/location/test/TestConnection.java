package com.agence.location.test;

import com.agence.location.dao.ConfigurationParkingDAO;
import com.agence.location.model.ConfigurationParking;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== Test de connexion à la base de données ===");
        
        ConfigurationParkingDAO dao = new ConfigurationParkingDAO();
        
        try {
            // Test lecture
            ConfigurationParking dakar = dao.findById("Dakar");
            
            if (dakar != null) {
                System.out.println("✓ Connexion BD OK");
                System.out.println("Parking : " + dakar.getNomParking());
                System.out.println("Capacité : " + dakar.getCapaciteMax());
            } else {
                System.out.println("✗ Aucune donnée trouvée pour Dakar");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erreur connexion : " + e.getMessage());
            e.printStackTrace();
        } finally {
            dao.close();
        }
    }
}