-- Insert semesters
INSERT INTO "semestre" ("libelle", "annee") VALUES
('Semestre 1', 'L1'),
('Semestre 2', 'L1'),
('Semestre 3', 'L2');

-- Insert academic years (assuming current year)
INSERT INTO "annee_scolaire" ("annee1", "annee2") VALUES
('2024-09-01', '2025-06-30'),
('2025-09-01', '2026-06-30');

-- Insert course groups (for obligatory courses)
INSERT INTO "groupe_matiere" ("libelle") VALUES
('Obligatoire');

-- Insert result mentions
INSERT INTO "mention_resultat" ("libelle", "moyenne_min", "moyenne_max") VALUES
('Très Bien', 16, 20),
('Bien', 14, 15),
('Assez Bien', 12, 13),
('Passable', 10, 11),
('Ajourné', 0, 9);

-- Insert exam sessions
INSERT INTO "session" ("libelle", "date_session") VALUES
('Session Normale', '2025-01-15'),
('Session Finale', '2025-02-20');

-- Insert course units for Semester 1
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF101', 'Programmation procédurale'),
('INF104', 'HTML et Introduction au Web'),
('INF107', 'Informatique de Base'),
('MTH101', 'Arithmétique et nombres'),
('MTH102', 'Analyse mathématique'),
('ORG101', 'Techniques de communication');

-- Insert course units for Semester 2
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF102', 'Bases de données relationnelles'),
('INF103', 'Bases de l\'administration système'),
('INF105', 'Maintenance matériel et logiciel'),
('INF106', 'Compléments de programmation'),
('MTH103', 'Calcul Vectoriel et Matriciel'),
('MTH105', 'Probabilité et Statistique');

-- Insert course units for Semester 3
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF201', 'Programmation orientée objet'),
('INF202', 'Bases de données objets'),
('INF203', 'Programmation système'),
('INF208', 'Réseaux informatiques'),
('MTH201', 'Méthodes numériques'),
('ORG201', 'Bases de gestion');

-- Insert options for each semester
INSERT INTO "option" ("libelle", "semestre_id") VALUES
('Connaissances Scientifiques et Techniques de Base', 1),
('Connaissances Scientifiques et Techniques de Base', 2),
('Connaissances Scientifiques et Techniques de Base', 3);

-- Link course units to Option for Semester 1 with credits
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 1 courses (option_id = 1)
(1, 1, 7, 1),  -- INF101
(1, 2, 5, 1),  -- INF104
(1, 3, 4, 1),  -- INF107
(1, 4, 4, 1),  -- MTH101
(1, 5, 6, 1),  -- MTH102
(1, 6, 4, 1),  -- ORG101

-- Semester 2 courses (option_id = 2)
(2, 7, 5, 1),  -- INF102
(2, 8, 5, 1),  -- INF103
(2, 9, 4, 1),  -- INF105
(2, 10, 6, 1), -- INF106
(2, 11, 6, 1), -- MTH103
(2, 12, 4, 1), -- MTH105

-- Semester 3 courses (option_id = 3)
(3, 13, 6, 1), -- INF201
(3, 14, 6, 1), -- INF202
(3, 15, 4, 1), -- INF203
(3, 16, 6, 1), -- INF208
(3, 17, 4, 1), -- MTH201
(3, 18, 4, 1); -- ORG201

INSERT INTO "semestre" ("libelle", "annee") VALUES
('Semestre 4', 'L2'),
('Semestre 5', 'L3'),
('Semestre 6', 'L3');

-- Option DEV

-- Insert course groups for optional courses
INSERT INTO "groupe_matiere" ("libelle") VALUES
('Groupe 1 - S4: 1 UE parmi SIG/SI/IHM'),
('Groupe 2 - S4: 1 UE parmi Géométrie/EquaDiff/Optimisation'),
('Groupe 3 - S6: 1 UE parmi Technologies réseaux/Multimédia');

-- Insert course units for Semester 4
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
-- Group 1 courses (1 UE parmi)
('INF204', 'Système d\'information géographique'),
('INF205', 'Système d\'information'),
('INF206', 'Interface Homme/Machine'),
-- Obligatory courses
('INF207', 'Eléments d\'algorithmique'),
('INF210', 'Mini-projet de développement'),
-- Group 2 courses (1 UE parmi)
('MTH204', 'Géométrie'),
('MTH205', 'Equations différentielles'),
('MTH206', 'Optimisation'),
-- Obligatory course
('MTH203', 'MAO');

-- Insert course units for Semester 5
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF301', 'Architecture logicielle'),
('INF304', 'Développement pour mobiles'),
('INF307', 'Conception en modèle orienté objet'),
('ORG301', 'Gestion d\'entreprise'),
('ORG302', 'Gestion de projets'),
('ORG303', 'Anglais pour les affaires');

-- Insert course units for Semester 6
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF310', 'Codage'),
('INF313', 'Programmation avancée, frameworks'),
-- Group 3 courses (1 UE parmi)
('INF302', 'Technologies d\'accès aux réseaux'),
('INF303', 'Multimédia'),
-- Obligatory courses
('INF316', 'Projet de développement'),
('ORG304', 'Communication d\'entreprise');

-- Insert options for each semester
INSERT INTO "option" ("libelle", "semestre_id") VALUES
('Développement', 4),  -- S4
('Développement', 5),  -- S5
('Développement', 6);  -- S6

-- Link course units to Option for Semester 4 with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 4 - Group 1 courses (1 UE parmi: INF204/INF205/INF206)
(4, 19, 6, 2),  -- INF204
(4, 20, 6, 2),  -- INF205
(4, 21, 6, 2),  -- INF206
-- Semester 4 - Obligatory courses
(4, 22, 6, 1),  -- INF207
(4, 23, 10, 1), -- INF210
-- Semester 4 - Group 2 courses (1 UE parmi: MTH204/MTH205/MTH206)
(4, 24, 4, 3),  -- MTH204
(4, 25, 4, 3),  -- MTH205
(4, 26, 4, 3),  -- MTH206
-- Semester 4 - Obligatory course
(4, 27, 4, 1);  -- MTH203

-- Link course units to Option for Semester 5 with credits (all obligatory)
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 5 courses (all obligatory)
(5, 28, 6, 1),  -- INF301
(5, 29, 6, 1),  -- INF304
(5, 30, 6, 1),  -- INF307
(5, 31, 5, 1),  -- ORG301
(5, 32, 4, 1),  -- ORG302
(5, 33, 3, 1);  -- ORG303

-- Link course units to Option for Semester 6 with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 6 - Obligatory courses
(6, 34, 4, 1),  -- INF310
(6, 35, 6, 1),  -- INF313
(6, 38, 10, 1), -- INF316
(6, 39, 4, 1),  -- ORG304
-- Semester 6 - Group 3 courses (1 UE parmi: INF302/INF303)
(6, 36, 6, 4),  -- INF302
(6, 37, 6, 4);  -- INF303

-- Option BDD et Reseau
-- Insert course groups for optional courses (adding to existing groups)
INSERT INTO "groupe_matiere" ("libelle") VALUES
('Groupe 5 - S4: 1 UE parmi SIG/IHM/Algorithmique'),
('Groupe 6 - S4: 1 UE parmi Analyse données/EquaDiff/Optimisation'),
('Groupe 7 - S6: 1 UE parmi Multimédia/Développement mobile');

-- Insert course units for Semester 4 (BDR)
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
-- Already exists: ('INF205', 'Système d\'information'),
-- Group 5 courses (1 UE parmi) - some may already exist
-- ('INF204', 'Système d\'information géographique'), -- exists
-- ('INF206', 'Interface Homme/Machine'), -- exists
-- ('INF207', 'Eléments d\'algorithmique'), -- exists
('INF211', 'Mini-projet de bases de données et/ou de réseaux'),
-- Group 6 courses (1 UE parmi) - some may already exist
('MTH202', 'Analyse des données');
-- ('MTH205', 'Equations différentielles'), -- exists
-- ('MTH206', 'Optimisation'), -- exists
-- ('MTH203', 'MAO') -- exists

-- Insert course units for Semester 5 (BDR)
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF305', 'Gestion de domaines, clustering'),
('INF308', 'Conception en modèle relationnel');
-- Already exist: 
-- ('INF302', 'Technologies d\'accès aux réseaux'),
-- ('ORG301', 'Gestion d\'entreprise'),
-- ('ORG302', 'Gestion de projets'),
-- ('ORG303', 'Anglais pour les affaires')

-- Insert course units for Semester 6 (BDR)
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF311', 'Datawarehouse et datamining'),
('INF314', 'Bases de données avancées'),
('INF317', 'Projet de Bases de données et/ou de réseaux');
-- Already exist:
-- ('INF303', 'Multimédia'),
-- ('INF304', 'Développement pour mobiles'),
-- ('ORG304', 'Communication d\'entreprise')

-- Insert options for BDR parcours
INSERT INTO "option" ("libelle", "semestre_id") VALUES
('Bases de Données et Réseaux', 4),  -- S4
('Bases de Données et Réseaux', 5),  -- S5
('Bases de Données et Réseaux', 6);  -- S6

-- Link course units to Option for Semester 4 (BDR) with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 4 - Obligatory course
(7, 20, 6, 1),  -- INF205 (Système d'information)
-- Semester 4 - Group 5 courses (1 UE parmi: INF204/INF206/INF207)
(7, 19, 6, 5),  -- INF204
(7, 21, 6, 5),  -- INF206
(7, 22, 6, 5),  -- INF207
-- Semester 4 - Obligatory course
(7, 40, 10, 1), -- INF211 (Mini-projet)
-- Semester 4 - Group 6 courses (1 UE parmi: MTH202/MTH205/MTH206)
(7, 41, 4, 6),  -- MTH202 (Analyse des données)
(7, 25, 4, 6),  -- MTH205
(7, 26, 4, 6),  -- MTH206
-- Semester 4 - Obligatory course
(7, 27, 4, 1);  -- MTH203

-- Link course units to Option for Semester 5 (BDR) with credits (all obligatory)
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 5 courses (all obligatory)
(8, 36, 6, 1),  -- INF302 (Technologies d'accès aux réseaux)
(8, 42, 6, 1),  -- INF305 (Gestion de domaines, clustering)
(8, 43, 6, 1),  -- INF308 (Conception en modèle relationnel)
(8, 31, 5, 1),  -- ORG301
(8, 32, 4, 1),  -- ORG302
(8, 33, 3, 1);  -- ORG303

-- Link course units to Option for Semester 6 (BDR) with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 6 - Obligatory courses
(9, 44, 4, 1),  -- INF311 (Datawarehouse et datamining)
(9, 45, 6, 1),  -- INF314 (Bases de données avancées)
(9, 46, 10, 1), -- INF317 (Projet)
(9, 39, 4, 1),  -- ORG304
-- Semester 6 - Group 7 courses (1 UE parmi: INF303/INF304)
(9, 37, 6, 7),  -- INF303 (Multimédia)
(9, 29, 6, 7);  -- INF304 (Développement pour mobiles)

-- Option WEB
-- Insert course groups for optional courses (adding to existing groups)
INSERT INTO "groupe_matiere" ("libelle") VALUES
('Groupe 8 - S4: 1 UE parmi SIG/SI/IHM'),
('Groupe 9 - S4: 1 UE parmi Analyse données/Géométrie/Optimisation'),
('Groupe 10 - S6: 1 UE parmi Technologies réseaux/Développement mobile');

-- Insert course units for Semester 4 (Web et Design)
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF209', 'Web dynamique'),
('INF212', 'Mini-projet de Web et design');
-- Already exist from previous parcours:
-- ('INF204', 'Système d\'information géographique'),
-- ('INF205', 'Système d\'information'),
-- ('INF206', 'Interface Homme/Machine'),
-- ('MTH202', 'Analyse des données'),
-- ('MTH204', 'Géométrie'),
-- ('MTH206', 'Optimisation'),
-- ('MTH203', 'MAO')

-- Insert course units for Semester 5 (Web et Design)
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF306', 'Introduction au cloud'),
('INF309', 'Web avancé');
-- Already exist:
-- ('INF303', 'Multimédia'),
-- ('ORG301', 'Gestion d\'entreprise'),
-- ('ORG302', 'Gestion de projets'),
-- ('ORG303', 'Anglais pour les affaires')

-- Insert course units for Semester 6 (Web et Design)
INSERT INTO "unite_enseignement" ("code_matiere", "intitule") VALUES
('INF312', 'Web design'),
('INF315', 'Web services'),
('INF318', 'Projet de Web et design');
-- Already exist:
-- ('INF302', 'Technologies d\'accès aux réseaux'),
-- ('INF304', 'Développement pour mobiles'),
-- ('ORG304', 'Communication d\'entreprise')

-- Insert options for Web et Design parcours
INSERT INTO "option" ("libelle", "semestre_id") VALUES
('Web et Design', 4),  -- S4
('Web et Design', 5),  -- S5
('Web et Design', 6);  -- S6

-- Link course units to Option for Semester 4 (Web et Design) with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 4 - Group 8 courses (1 UE parmi: INF204/INF205/INF206)
(10, 19, 6, 8),  -- INF204
(10, 20, 6, 8),  -- INF205
(10, 21, 6, 8),  -- INF206
-- Semester 4 - Obligatory courses
(10, 47, 6, 1),  -- INF209 (Web dynamique)
(10, 48, 10, 1), -- INF212 (Mini-projet)
-- Semester 4 - Group 9 courses (1 UE parmi: MTH202/MTH204/MTH206)
(10, 41, 4, 9),  -- MTH202 (Analyse des données)
(10, 24, 4, 9),  -- MTH204 (Géométrie)
(10, 26, 4, 9),  -- MTH206 (Optimisation)
-- Semester 4 - Obligatory course
(10, 27, 4, 1);  -- MTH203

-- Link course units to Option for Semester 5 (Web et Design) with credits (all obligatory)
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 5 courses (all obligatory)
(11, 37, 6, 1),  -- INF303 (Multimédia)
(11, 49, 6, 1),  -- INF306 (Introduction au cloud)
(11, 50, 6, 1),  -- INF309 (Web avancé)
(11, 31, 5, 1),  -- ORG301
(11, 32, 4, 1),  -- ORG302
(11, 33, 3, 1);  -- ORG303

-- Link course units to Option for Semester 6 (Web et Design) with credits and groups
INSERT INTO "option_unite_enseignement" ("option_id", "unite_enseignement_id", "credits", "groupe_id") VALUES
-- Semester 6 - Obligatory courses
(12, 51, 4, 1),  -- INF312 (Web design)
(12, 52, 6, 1),  -- INF315 (Web services)
(12, 53, 10, 1), -- INF318 (Projet)
(12, 39, 4, 1),  -- ORG304
-- Semester 6 - Group 10 courses (1 UE parmi: INF302/INF304)
(12, 36, 6, 10), -- INF302 (Technologies d'accès aux réseaux)
(12, 29, 6, 10); -- INF304 (Développement pour mobiles)

INSERT INTO users (username, password) VALUES ('admin', 'admin');