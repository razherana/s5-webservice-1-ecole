INSERT INTO semestre (libelle, annee) VALUES
('Semestre 1', 'L1'),
('Semestre 2', 'L1'),
('Semestre 3', 'L2'),
('Semestre 4', 'L2'),
('Semestre 5', 'L3'),
('Semestre 6', 'L3');

---Table option
INSERT INTO "option" (libelle, semestre_id) VALUES
('Connaissances Scientifiques et Techniques de Base', 1), -- id 1
('Connaissances Scientifiques et Techniques de Base', 2), -- id 2
('Connaissances Scientifiques et Techniques de Base', 3), -- id 3

('Développement', 4), -- id 4
('Développement', 5), -- id 5
('Développement', 6), -- id 6

('Bases de Données et Réseaux', 4), -- id 7
('Bases de Données et Réseaux', 5), -- id 8
('Bases de Données et Réseaux', 6), -- id 9

('Web et Design', 4), -- id 10
('Web et Design', 5), -- id 11
('Web et Design', 6); -- id 12



-- Correction de la table groupe_matiere
INSERT INTO groupe_matiere (libelle) VALUES
('Obligatoire'),
('S4-Dev-G1'),('S4-Dev-G2'),
('S4-BDR-G1'),('S4-BDR-G2'),
('S4-Web-G1'),('S4-Web-G2'),
('S6-Dev-G1'),('S6-BDR-G1'),('S6-Web-G1');




---Semestre 1
INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
('INF101', 'Programmation procédurale'),
('INF104', 'HTML et Introduction au Web'),
('INF107', 'Informatique de Base'),
('MTH101', 'Arithmétique et nombres'),
('MTH102', 'Analyse mathématique'),
('ORG101', 'Techniques de communication');

INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(1, 1, 7, 1),
(1, 2, 5, 1),
(1, 3, 4, 1),
(1, 4, 4, 1),
(1, 5, 6, 1),
(1, 6, 4, 1);

---Semestre 2
INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
('INF102', 'Bases de données relationnelles'),
('INF103', 'Bases de l’administration système'),
('INF105', 'Maintenance matériel et logiciel'),
('INF106', 'Compléments de programmation'),
('MTH103', 'Calcul Vectoriel et Matriciel'),
('MTH105', 'Probabilité et Statistique');

INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(2, 7, 5, 1),
(2, 8, 5, 1),
(2, 9, 4, 1),
(2, 10, 6, 1),
(2, 11, 6, 1),
(2, 12, 4, 1);

---Semestre 3
INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
('INF201', 'Programmation orientée objet'),
('INF202', 'Bases de données objets'),
('INF203', 'Programmation système'),
('INF208', 'Réseaux informatiques'),
('MTH201', 'Méthodes numériques'),
('ORG201', 'Bases de gestion');

INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(3, 13, 6, 1),
(3, 14, 6, 1),
(3, 15, 4, 1),
(3, 16, 6, 1),
(3, 17, 4, 1),
(3, 18, 4, 1);



---Unite d'enseignement des options Semestre 4,5,6
INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
('INF204','Système d’information géographique'),
('INF205','Système d’information'),
('INF206','Interface Homme/Machine'),
('INF207','Éléments d’algorithmique'),
('INF210','Mini-projet de développement'),
('MTH204','Géométrie'),
('MTH205','Équations différentielles'),
('MTH206','Optimisation'),
('MTH203','MAO'),
('INF301','Architecture logicielle'),
('INF304','Développement pour mobiles'),
('INF307','Conception en modèle orienté objet'),
('ORG301','Gestion d’entreprise'),
('ORG302','Gestion de projets'),
('ORG303','Anglais pour les affaires'),
('INF310','Codage'),
('INF313','Programmation avancée, frameworks'),
('INF302','Technologies d’accès aux réseaux'),
('INF303','Multimédia'),
('INF316','Projet de développement'),
('ORG304','Communication d’entreprise'),
('INF305','Gestion de domaines, clustering'),
('INF308','Conception en modèle relationnel'),
('INF311','Datawarehouse et datamining'),
('INF314','Bases de données avancées'),
('INF317','Projet BDD & réseaux'),
('INF209','Web dynamique'),
('INF212','Mini-projet Web et design'),
('MTH202','Analyse des données'),
('INF306','Introduction au cloud'),
('INF309','Web avancé'),
('INF312','Web design'),
('INF315','Web services'),
('INF318','Projet de Web & design');


---Parcours développement Semestre 4,5,6
-- Semestre 4 (option_id 4)
-- Parcours développement Semestre 4 (option_id 4) - CORRIGÉ
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- Groupe 1 : Au choix 1 UE parmi (même groupe_id)
(4, 19, 6, 2),  -- INF204
(4, 20, 6, 2),  -- INF205  
(4, 21, 6, 2),  -- INF206
-- UE obligatoires
(4, 22, 6, 1),  -- INF207 (obligatoire)
(4, 23, 10, 1), -- INF210 (obligatoire)
-- Groupe 2 : Au choix 1 UE parmi (même groupe_id)
(4, 24, 4, 3),  -- MTH204
(4, 25, 4, 3),  -- MTH205
(4, 26, 4, 3),  -- MTH206
-- UE obligatoire
(4, 27, 4, 1);  -- MTH203 (obligatoire)


-- Semestre 5 (option_id 5)
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(5, 10, 6, 1),
(5, 11, 6, 1),
(5, 12, 6, 1),
(5, 13, 5, 1),
(5, 14, 4, 1),
(5, 15, 3, 1);



-- Semestre 6 (option_id 6)
-- Semestre 6 Développement (option_id 6) - CORRIGÉ
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(6, 16, 4, 1),  -- INF310 (obligatoire)
(6, 17, 6, 1),  -- INF313 (obligatoire)
-- Groupe optionnel : Au choix 1 UE parmi (même groupe_id)
(6, 18, 6, 8),  -- INF302
(6, 19, 6, 8),  -- INF303
-- UE obligatoire
(6, 20, 10, 1), -- INF316
(6, 21, 4, 1);  -- ORG304



---Parcours Bases de Données et Réseaux Semestre 4,5,6
-- Semestre 4 (option_id 7)
-- Parcours Bases de Données et Réseaux Semestre 4 (option_id 7) - CORRIGÉ
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- UE obligatoire
(7, 20, 6, 1),  -- INF205 (obligatoire)
-- Groupe 1 : Au choix 1 UE parmi (même groupe_id)
(7, 19, 6, 4),  -- INF204
(7, 21, 6, 4),  -- INF206
(7, 22, 6, 4),  -- INF207
-- UE obligatoire
(7, 28, 10, 1), -- INF211 (obligatoire)
-- Groupe 2 : Au choix 1 UE parmi (même groupe_id)
(7, 29, 4, 5),  -- MTH202
(7, 25, 4, 5),  -- MTH205
(7, 26, 4, 5),  -- MTH206
-- UE obligatoire
(7, 27, 4, 1);  -- MTH203 (obligatoire)



-- Semestre 5 (option_id 8)
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(8, 18, 6, 1),
(8, 22, 6, 1),
(8, 23, 6, 1),
(8, 13, 5, 1),
(8, 14, 4, 1),
(8, 15, 3, 1);



-- Semestre 6 (option_id 9)
-- Semestre 6 BDR (option_id 9) - CORRIGÉ
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(9, 24, 4, 1),  -- INF311 (obligatoire)
(9, 25, 6, 1),  -- INF314 (obligatoire)
-- Groupe optionnel : Au choix 1 UE parmi (même groupe_id)
(9, 19, 6, 9),  -- INF303
(9, 11, 6, 9),  -- INF304
-- UE obligatoire
(9, 26, 10, 1), -- INF317
(9, 21, 4, 1);  -- ORG304



---Parcours Web et Design Semestre 4,5,6
-- Semestre 4 (option_id 10)
-- Parcours Web et Design Semestre 4 (option_id 10) - CORRIGÉ
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- Groupe 1 : Au choix 1 UE parmi (même groupe_id)
(10, 19, 6, 6),  -- INF204
(10, 20, 6, 6),  -- INF205
(10, 21, 6, 6),  -- INF206
-- UE obligatoires
(10, 30, 6, 1),  -- INF209 (obligatoire)
(10, 31, 10, 1), -- INF212 (obligatoire)
-- Groupe 2 : Au choix 1 UE parmi (même groupe_id)
(10, 29, 4, 7),  -- MTH202
(10, 24, 4, 7),  -- MTH204
(10, 26, 4, 7),  -- MTH206
-- UE obligatoire
(10, 27, 4, 1);  -- MTH203 (obligatoire)



-- Semestre 5 (option_id 11)
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(11, 19, 6, 1),
(11, 30, 6, 1),
(11, 31, 6, 1),
(11, 13, 5, 1),
(11, 14, 4, 1),
(11, 15, 3, 1);



-- Semestre 6 (option_id 12)
-- Semestre 6 Web et Design (option_id 12) - CORRIGÉ
INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
(12, 32, 4, 1),  -- INF312 (obligatoire)
(12, 33, 6, 1),  -- INF315 (obligatoire)
-- Groupe optionnel : Au choix 1 UE parmi (même groupe_id)
(12, 18, 6, 10),  -- INF302
(12, 11, 6, 10),  -- INF304
-- UE obligatoire
(12, 34, 10, 1), -- INF318
(12, 21, 4, 1);   -- ORG304



---Notes et etudiants
INSERT INTO annee_scolaire (annee1, annee2) VALUES
('2022-09-01', '2023-07-31'),
('2023-09-01', '2024-07-31'),
('2024-09-01', '2025-07-31');

-- Insertion des sessions d'examen
INSERT INTO session (libelle, date_session) VALUES
('Session Normale 2023', '2023-06-15'),
('Session Rattrapage 2023', '2023-09-10'),
('Session Normale 2024', '2024-06-20'),
('Session Rattrapage 2024', '2024-09-05'),
('Session Normale 2025', '2025-06-18');

-- Insertion des mentions de résultat
INSERT INTO mention_resultat (libelle, moyenne_min, moyenne_max) VALUES
('Très Bien', 16, 20),
('Bien', 14, 15.99),
('Assez Bien', 12, 13.99),
('Passable', 10, 11.99),
('Ajourné', 0, 9.99);

-- Insertion des étudiants
INSERT INTO etudiant (nom, prenom, date_naissance) VALUES
('Rakoto', 'Jean', '2002-03-15'),
('Rasoa', 'Marie', '2001-11-22'),
('Randria', 'Pierre', '2002-07-08'),
('Razafy', 'Sophie', '2001-12-30'),
('Andriana', 'Luc', '2002-05-18'),
('Ravo', 'Claire', '2001-09-14'),
('Rabe', 'Thomas', '2002-02-28'),
('Rasolo', 'Elodie', '2001-08-11'),
('Rajaona', 'Marc', '2002-04-03'),
('Ramanana', 'Sarah', '2001-10-25');

-- Insertion des inscriptions pour l'année scolaire 2023-2024
INSERT INTO inscription (date_inscription, annee_scolaire_id, semestre_id, etudiant_id) VALUES
-- Semestre 1
('2023-09-05', 2, 1, 1),
('2023-09-05', 2, 1, 2),
('2023-09-05', 2, 1, 3),
('2023-09-05', 2, 1, 4),
('2023-09-05', 2, 1, 5),

-- Semestre 2
('2024-01-15', 2, 2, 1),
('2024-01-15', 2, 2, 2),
('2024-01-15', 2, 2, 3),
('2024-01-15', 2, 2, 4),
('2024-01-15', 2, 2, 5),

-- Semestre 3
('2023-09-05', 2, 3, 6),
('2023-09-05', 2, 3, 7),
('2023-09-05', 2, 3, 8),
('2023-09-05', 2, 3, 9),
('2023-09-05', 2, 3, 10),

-- Semestre 4 - Développement
('2024-01-15', 2, 4, 6),
('2024-01-15', 2, 4, 7),
-- Semestre 4 - BDR
('2024-01-15', 2, 4, 8),
-- Semestre 4 - Web et Design
('2024-01-15', 2, 4, 9),
('2024-01-15', 2, 4, 10);

-- Insertion des notes pour le semestre 1 (session normale 2023)
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Étudiant 1 - Semestre 1
(15.5, 1, 1, 1), (14.0, 1, 1, 2), (16.2, 1, 1, 3), (12.8, 1, 1, 4), (13.5, 1, 1, 5), (11.9, 1, 1, 6),
-- Étudiant 2 - Semestre 1
(17.2, 2, 1, 1), (15.8, 2, 1, 2), (14.5, 2, 1, 3), (16.1, 2, 1, 4), (15.3, 2, 1, 5), (13.7, 2, 1, 6),
-- Étudiant 3 - Semestre 1
(12.3, 3, 1, 1), (11.5, 3, 1, 2), (13.8, 3, 1, 3), (10.9, 3, 1, 4), (14.2, 3, 1, 5), (12.1, 3, 1, 6),
-- Étudiant 4 - Semestre 1
(18.0, 4, 1, 1), (16.5, 4, 1, 2), (17.8, 4, 1, 3), (15.2, 4, 1, 4), (16.9, 4, 1, 5), (14.3, 4, 1, 6),
-- Étudiant 5 - Semestre 1
(9.8, 5, 1, 1), (10.2, 5, 1, 2), (8.5, 5, 1, 3), (11.1, 5, 1, 4), (9.3, 5, 1, 5), (10.7, 5, 1, 6);

-- Insertion des notes pour le semestre 2 (session normale 2024)
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Étudiant 1 - Semestre 2
(14.8, 6, 3, 7), (15.3, 6, 3, 8), (13.9, 6, 3, 9), (16.1, 6, 3, 10), (12.7, 6, 3, 11), (14.2, 6, 3, 12),
-- Étudiant 2 - Semestre 2
(16.5, 7, 3, 7), (17.2, 7, 3, 8), (15.8, 7, 3, 9), (14.9, 7, 3, 10), (16.3, 7, 3, 11), (15.1, 7, 3, 12),
-- Étudiant 3 - Semestre 2
(11.2, 8, 3, 7), (12.8, 8, 3, 8), (10.5, 8, 3, 9), (13.1, 8, 3, 10), (11.9, 8, 3, 11), (12.4, 8, 3, 12),
-- Étudiant 4 - Semestre 2
(17.8, 9, 3, 7), (16.9, 9, 3, 8), (18.2, 9, 3, 9), (15.7, 9, 3, 10), (17.1, 9, 3, 11), (16.4, 9, 3, 12),
-- Étudiant 5 - Semestre 2 (notes basses pour démontrer les rattrapages)
(8.5, 10, 3, 7), (7.9, 10, 3, 8), (9.2, 10, 3, 9), (8.1, 10, 3, 10), (7.5, 10, 3, 11), (8.8, 10, 3, 12);

-- Insertion des notes de rattrapage pour l'étudiant 5
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
(12.5, 10, 4, 7), (11.9, 10, 4, 8), (13.2, 10, 4, 9), (10.8, 10, 4, 10), (12.1, 10, 4, 11), (11.5, 10, 4, 12);

-- Insertion des notes pour le semestre 3 (session normale 2024)
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Étudiant 6 - Semestre 3
(15.2, 11, 3, 13), (14.7, 11, 3, 14), (16.1, 11, 3, 15), (13.8, 11, 3, 16), (15.9, 11, 3, 17), (14.3, 11, 3, 18),
-- Étudiant 7 - Semestre 3
(17.1, 12, 3, 13), (16.4, 12, 3, 14), (15.8, 12, 3, 15), (17.3, 12, 3, 16), (16.7, 12, 3, 17), (15.2, 12, 3, 18),
-- Étudiant 8 - Semestre 3
(12.8, 13, 3, 13), (13.5, 13, 3, 14), (11.9, 13, 3, 15), (14.1, 13, 3, 16), (12.3, 13, 3, 17), (13.7, 13, 3, 18),
-- Étudiant 9 - Semestre 3
(18.2, 14, 3, 13), (17.5, 14, 3, 14), (16.9, 14, 3, 15), (18.1, 14, 3, 16), (17.8, 14, 3, 17), (16.4, 14, 3, 18),
-- Étudiant 10 - Semestre 3
(10.5, 15, 3, 13), (11.2, 15, 3, 14), (9.8, 15, 3, 15), (12.1, 15, 3, 16), (10.7, 15, 3, 17), (11.4, 15, 3, 18);

-- Insertion des notes pour le semestre 4 - Parcours Développement (session normale 2024)
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Étudiant 6 - Semestre 4 Développement
(15.5, 16, 3, 19), -- INF204 (choisi dans le groupe optionnel)
(14.2, 16, 3, 22), -- INF207 (obligatoire)
(16.8, 16, 3, 23), -- INF210 (obligatoire)
(13.9, 16, 3, 24), -- MTH204 (choisi dans le groupe optionnel)
(12.7, 16, 3, 27), -- MTH203 (obligatoire)
-- Étudiant 7 - Semestre 4 Développement
(16.3, 17, 3, 20), -- INF205 (choisi dans le groupe optionnel)
(15.7, 17, 3, 22), -- INF207 (obligatoire)
(17.1, 17, 3, 23), -- INF210 (obligatoire)
(14.5, 17, 3, 25), -- MTH205 (choisi dans le groupe optionnel)
(13.2, 17, 3, 27); -- MTH203 (obligatoire)

-- Insertion des notes pour le semestre 4 - Parcours BDR (session normale 2024)
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Étudiant 8 - Semestre 4 BDR
(14.8, 18, 3, 20), -- INF205 (obligatoire)
(15.3, 18, 3, 21), -- INF206 (choisi dans le groupe optionnel)
(16.1, 18, 3, 28), -- INF211 (obligatoire)
(13.7, 18, 3, 29), -- MTH202 (choisi dans le groupe optionnel)
(12.9, 18, 3, 27); -- MTH203 (obligatoire)

-- Insertion des notes pour le semestre 4 - Parcours Web et Design (session normale 2024)
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Étudiant 9 - Semestre 4 Web et Design
(16.2, 19, 3, 19), -- INF204 (choisi dans le groupe optionnel)
(15.8, 19, 3, 30), -- INF209 (obligatoire)
(17.3, 19, 3, 31), -- INF212 (obligatoire)
(14.1, 19, 3, 24), -- MTH204 (choisi dans le groupe optionnel)
(13.5, 19, 3, 27), -- MTH203 (obligatoire)
-- Étudiant 10 - Semestre 4 Web et Design
(14.7, 20, 3, 21), -- INF206 (choisi dans le groupe optionnel)
(13.9, 20, 3, 30), -- INF209 (obligatoire)
(15.4, 20, 3, 31), -- INF212 (obligatoire)
(12.8, 20, 3, 26), -- MTH206 (choisi dans le groupe optionnel)
(11.6, 20, 3, 27); -- MTH203 (obligatoire)


