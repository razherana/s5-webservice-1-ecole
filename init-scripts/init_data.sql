-- Clear existing data (optional - use only if starting fresh)
-- TRUNCATE TABLE "option_unite_enseignement", "notes", "inscription", "option", "unite_enseignement", "groupe_matiere", "session", "mention_resultat", "annee_scolaire", "semestre" RESTART IDENTITY CASCADE;

-- Insert semesters with forced IDs
INSERT INTO "semestre" ("id", "libelle", "annee") VALUES
(1, 'Semestre 1', 'L1'),
(2, 'Semestre 2', 'L1'),
(3, 'Semestre 3', 'L2'),
(4, 'Semestre 4', 'L2'),
(5, 'Semestre 5', 'L3'),
(6, 'Semestre 6', 'L3');

-- Insert academic years with forced IDs
INSERT INTO "annee_scolaire" ("id", "annee1", "annee2") VALUES
(1, '2024-09-01', '2025-06-30'),
(2, '2025-09-01', '2026-06-30');

-- Insert course groups with forced IDs
INSERT INTO "groupe_matiere" ("id", "libelle") VALUES
(1, 'Obligatoire'),
(2, 'Groupe 1 - S4 DEV: 1 UE parmi SIG/SI/IHM'),
(3, 'Groupe 2 - S4 DEV: 1 UE parmi Géométrie/EquaDiff/Optimisation'),
(4, 'Groupe 3 - S6 DEV: 1 UE parmi Technologies réseaux/Multimédia'),
(5, 'Groupe 4 - S4 BDR: 1 UE parmi SIG/IHM/Algorithmique'),
(6, 'Groupe 5 - S4 BDR: 1 UE parmi Analyse données/EquaDiff/Optimisation'),
(7, 'Groupe 6 - S6 BDR: 1 UE parmi Multimédia/Développement mobile'),
(8, 'Groupe 7 - S4 WEB: 1 UE parmi SIG/SI/IHM'),
(9, 'Groupe 8 - S4 WEB: 1 UE parmi Analyse données/Géométrie/Optimisation'),
(10, 'Groupe 9 - S6 WEB: 1 UE parmi Technologies réseaux/Développement mobile');

-- Insert result mentions with forced IDs
INSERT INTO "mention_resultat" ("id", "libelle", "moyenne_min", "moyenne_max") VALUES
(1, 'Très Bien', 16, 20),
(2, 'Bien', 14, 15),
(3, 'Assez Bien', 12, 13),
(4, 'Passable', 10, 11),
(5, 'Ajourné', 0, 9);

-- Insert exam sessions with forced IDs
INSERT INTO "session" ("id", "libelle", "date_session") VALUES
(1, 'Session Normale', '2025-01-15'),
(2, 'Session Finale', '2025-02-20');

-- Insert ALL course units with forced IDs
INSERT INTO "unite_enseignement" ("id", "code_matiere", "intitule") VALUES
-- Semester 1-3 (Common core)
(1, 'INF101', 'Programmation procédurale'),
(2, 'INF104', 'HTML et Introduction au Web'),
(3, 'INF107', 'Informatique de Base'),
(4, 'MTH101', 'Arithmétique et nombres'),
(5, 'MTH102', 'Analyse mathématique'),
(6, 'ORG101', 'Techniques de communication'),
-- Semester 2
(7, 'INF102', 'Bases de données relationnelles'),
(8, 'INF103', 'Bases de l''administration système'),
(9, 'INF105', 'Maintenance matériel et logiciel'),
(10, 'INF106', 'Compléments de programmation'),
(11, 'MTH103', 'Calcul Vectoriel et Matriciel'),
(12, 'MTH105', 'Probabilité et Statistique'),
-- Semester 3
(13, 'INF201', 'Programmation orientée objet'),
(14, 'INF202', 'Bases de données objets'),
(15, 'INF203', 'Programmation système'),
(16, 'INF208', 'Réseaux informatiques'),
(17, 'MTH201', 'Méthodes numériques'),
(18, 'ORG201', 'Bases de gestion'),
-- Semester 4 courses
(19, 'INF204', 'Système d''information géographique'),
(20, 'INF205', 'Système d''information'),
(21, 'INF206', 'Interface Homme/Machine'),
(22, 'INF207', 'Eléments d''algorithmique'),
(23, 'INF209', 'Web dynamique'),
(24, 'INF210', 'Mini-projet de développement'),
(25, 'INF211', 'Mini-projet de bases de données et/ou de réseaux'),
(26, 'INF212', 'Mini-projet de Web et design'),
(27, 'MTH202', 'Analyse des données'),
(28, 'MTH203', 'MAO'),
(29, 'MTH204', 'Géométrie'),
(30, 'MTH205', 'Equations différentielles'),
(31, 'MTH206', 'Optimisation'),
-- Semester 5 courses
(32, 'INF301', 'Architecture logicielle'),
(33, 'INF302', 'Technologies d''accès aux réseaux'),
(34, 'INF303', 'Multimédia'),
(35, 'INF304', 'Développement pour mobiles'),
(36, 'INF305', 'Gestion de domaines, clustering'),
(37, 'INF306', 'Introduction au cloud'),
(38, 'INF307', 'Conception en modèle orienté objet'),
(39, 'INF308', 'Conception en modèle relationnel'),
(40, 'INF309', 'Web avancé'),
(41, 'ORG301', 'Gestion d''entreprise'),
(42, 'ORG302', 'Gestion de projets'),
(43, 'ORG303', 'Anglais pour les affaires'),
-- Semester 6 courses
(44, 'INF310', 'Codage'),
(45, 'INF311', 'Datawarehouse et datamining'),
(46, 'INF312', 'Web design'),
(47, 'INF313', 'Programmation avancée, frameworks'),
(48, 'INF314', 'Bases de données avancées'),
(49, 'INF315', 'Web services'),
(50, 'INF316', 'Projet de développement'),
(51, 'INF317', 'Projet de Bases de données et/ou de réseaux'),
(52, 'INF318', 'Projet de Web et design'),
(53, 'ORG304', 'Communication d''entreprise');

-- Insert options for all semesters and parcours with forced IDs
INSERT INTO "option" ("id", "libelle", "semestre_id") VALUES
-- Common core (S1-S3)
(1, 'Connaissances Scientifiques et Techniques de Base', 1),
(2, 'Connaissances Scientifiques et Techniques de Base', 2),
(3, 'Connaissances Scientifiques et Techniques de Base', 3),
-- Développement (S4-S6)
(4, 'Développement', 4),
(5, 'Développement', 5),
(6, 'Développement', 6),
-- Bases de Données et Réseaux (S4-S6)
(7, 'Bases de Données et Réseaux', 4),
(8, 'Bases de Données et Réseaux', 5),
(9, 'Bases de Données et Réseaux', 6),
-- Web et Design (S4-S6)
(10, 'Web et Design', 4),
(11, 'Web et Design', 5),
(12, 'Web et Design', 6);

-- Link course units to Options with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Common Core (S1-S3) - All obligatory
-- Semester 1 (option_id = 1)
(1, 1, 7, 1), (1, 2, 5, 1), (1, 3, 4, 1), (1, 4, 4, 1), (1, 5, 6, 1), (1, 6, 4, 1),
-- Semester 2 (option_id = 2)
(2, 7, 5, 1), (2, 8, 5, 1), (2, 9, 4, 1), (2, 10, 6, 1), (2, 11, 6, 1), (2, 12, 4, 1),
-- Semester 3 (option_id = 3)
(3, 13, 6, 1), (3, 14, 6, 1), (3, 15, 4, 1), (3, 16, 6, 1), (3, 17, 4, 1), (3, 18, 4, 1),

-- Développement Parcours
-- Semester 4 DEV (option_id = 4)
(4, 19, 6, 2), (4, 20, 6, 2), (4, 21, 6, 2),  -- Group 1: SIG/SI/IHM
(4, 22, 6, 1), (4, 24, 10, 1),                -- Obligatory: Algorithmique, Mini-projet
(4, 29, 4, 3), (4, 30, 4, 3), (4, 31, 4, 3),  -- Group 2: Géométrie/EquaDiff/Optimisation
(4, 28, 4, 1),                                -- Obligatory: MAO
-- Semester 5 DEV (option_id = 5) - All obligatory
(5, 32, 6, 1), (5, 35, 6, 1), (5, 38, 6, 1),  -- Architecture, Mobile, Conception OBJ
(5, 41, 5, 1), (5, 42, 4, 1), (5, 43, 3, 1),  -- Gestion entreprise, projets, anglais
-- Semester 6 DEV (option_id = 6)
(6, 44, 4, 1), (6, 47, 6, 1), (6, 50, 10, 1), (6, 53, 4, 1),  -- Obligatory: Codage, Frameworks, Projet, Communication
(6, 33, 6, 4), (6, 34, 6, 4),                                 -- Group 3: Technologies réseaux/Multimédia

-- Bases de Données et Réseaux Parcours
-- Semester 4 BDR (option_id = 7)
(7, 20, 6, 1),                                -- Obligatory: Système d'information
(7, 19, 6, 5), (7, 21, 6, 5), (7, 22, 6, 5), -- Group 4: SIG/IHM/Algorithmique
(7, 25, 10, 1),                               -- Obligatory: Mini-projet BDR
(7, 27, 4, 6), (7, 30, 4, 6), (7, 31, 4, 6), -- Group 5: Analyse données/EquaDiff/Optimisation
(7, 28, 4, 1),                               -- Obligatory: MAO
-- Semester 5 BDR (option_id = 8) - All obligatory
(8, 33, 6, 1), (8, 36, 6, 1), (8, 39, 6, 1),  -- Technologies réseaux, Clustering, Conception REL
(8, 41, 5, 1), (8, 42, 4, 1), (8, 43, 3, 1),  -- Gestion entreprise, projets, anglais
-- Semester 6 BDR (option_id = 9)
(9, 45, 4, 1), (9, 48, 6, 1), (9, 51, 10, 1), (9, 53, 4, 1),  -- Obligatory: Datawarehouse, BDD avancées, Projet, Communication
(9, 34, 6, 7), (9, 35, 6, 7),                                 -- Group 6: Multimédia/Développement mobile

-- Web et Design Parcours
-- Semester 4 WEB (option_id = 10)
(10, 19, 6, 8), (10, 20, 6, 8), (10, 21, 6, 8),  -- Group 7: SIG/SI/IHM
(10, 23, 6, 1), (10, 26, 10, 1),                 -- Obligatory: Web dynamique, Mini-projet Web
(10, 27, 4, 9), (10, 29, 4, 9), (10, 31, 4, 9), -- Group 8: Analyse données/Géométrie/Optimisation
(10, 28, 4, 1),                                 -- Obligatory: MAO
-- Semester 5 WEB (option_id = 11) - All obligatory
(11, 34, 6, 1), (11, 37, 6, 1), (11, 40, 6, 1),  -- Multimédia, Cloud, Web avancé
(11, 41, 5, 1), (11, 42, 4, 1), (11, 43, 3, 1),  -- Gestion entreprise, projets, anglais
-- Semester 6 WEB (option_id = 12)
(12, 46, 4, 1), (12, 49, 6, 1), (12, 52, 10, 1), (12, 53, 4, 1),  -- Obligatory: Web design, Web services, Projet, Communication
(12, 33, 6, 10), (12, 35, 6, 10);                                 -- Group 9: Technologies réseaux/Développement mobile

INSERT INTO users (username, password) VALUES ('admin', 'admin');