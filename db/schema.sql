-- Créer base de données
CREATE DATABASE IF NOT EXISTS location_voitures
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE location_voitures;

-- Table CONFIGURATION_PARKING (NOUVEAU)
CREATE TABLE configuration_parking (
    region VARCHAR(50) PRIMARY KEY,
    capacite_max INT NOT NULL CHECK (capacite_max > 0),
    nom_parking VARCHAR(100) NOT NULL,
    adresse VARCHAR(200),
    INDEX idx_region (region)
) ENGINE=InnoDB;

-- Données initiales
INSERT INTO configuration_parking VALUES
('Dakar', 30, 'Parking Central Dakar', 'Avenue Bourguiba, Dakar'),
('Ziguinchor', 25, 'Parking Ziguinchor', 'Boulevard du Général de Gaulle'),
('Thiès', 15, 'Parking Thiès', 'Avenue Lamine Gueye');

-- Table CLIENTS
CREATE TABLE clients (
    cin VARCHAR(20) PRIMARY KEY,
    permis VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    sexe CHAR(1) NOT NULL CHECK (sexe IN ('H', 'F')),
    adresse VARCHAR(200),
    email VARCHAR(100),
    telephone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    signature TEXT,
    date_inscription DATE DEFAULT (CURRENT_DATE),
    INDEX idx_email (email),
    INDEX idx_nom (nom)
) ENGINE=InnoDB;

-- Table VOITURES (avec FK vers configuration_parking)
CREATE TABLE voitures (
    immatriculation VARCHAR(20) PRIMARY KEY,
    nb_places INT NOT NULL CHECK (nb_places > 0),
    marque VARCHAR(50) NOT NULL,
    modele VARCHAR(50) NOT NULL,
    date_mise_circulation DATE NOT NULL,
    kilometrage DOUBLE NOT NULL CHECK (kilometrage >= 0),
    type_carburant VARCHAR(20) NOT NULL,
    categorie VARCHAR(30) NOT NULL,
    prix_location_j DOUBLE NOT NULL CHECK (prix_location_j > 0),
    statut ENUM('DISPONIBLE', 'LOUEE', 'EN_MAINTENANCE') NOT NULL DEFAULT 'DISPONIBLE',
    region VARCHAR(50) NOT NULL,
    photo_url VARCHAR(255),
    FOREIGN KEY (region) REFERENCES configuration_parking(region) ON DELETE RESTRICT,
    INDEX idx_statut (statut),
    INDEX idx_region (region),
    INDEX idx_marque (marque)
) ENGINE=InnoDB;

-- Table UTILISATEURS
CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    date_recrutement DATE NOT NULL,
    adresse VARCHAR(200),
    email VARCHAR(100),
    telephone VARCHAR(20),
    role ENUM('GESTIONNAIRE', 'CHEF_AGENCE') NOT NULL,
    signature TEXT,
    INDEX idx_username (username)
) ENGINE=InnoDB;

-- Table LOCATIONS
CREATE TABLE locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_cin VARCHAR(20) NOT NULL,
    voiture_immat VARCHAR(20) NOT NULL,
    utilisateur_id BIGINT,
    date_debut DATE NOT NULL,
    nombre_jours INT NOT NULL CHECK (nombre_jours > 0),
    date_retour_prevue DATE NOT NULL,
    date_retour_reelle DATE,
    kilometrage_depart DOUBLE,
    kilometrage_retour DOUBLE,
    montant_location DOUBLE NOT NULL CHECK (montant_location >= 0),
    dommages_constates BOOLEAN DEFAULT FALSE,
    montant_dommages DOUBLE DEFAULT 0,
    montant_total DOUBLE NOT NULL,
    statut ENUM('EN_ATTENTE', 'EN_COURS', 'TERMINEE', 'ANNULEE', 'ANNULEE_PAR_CLIENT') NOT NULL,
    signature_client TEXT,
    signature_gestionnaire TEXT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_cin) REFERENCES clients(cin) ON DELETE RESTRICT,
    FOREIGN KEY (voiture_immat) REFERENCES voitures(immatriculation) ON DELETE RESTRICT,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE SET NULL,
    INDEX idx_statut (statut),
    INDEX idx_date_debut (date_debut)
) ENGINE=InnoDB;

-- Utilisateur admin par défaut
INSERT INTO utilisateurs (username, password, prenom, nom, date_recrutement, role)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'Admin', 'System', CURRENT_DATE, 'CHEF_AGENCE');
-- Mot de passe : admin123