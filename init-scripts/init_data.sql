-- Insertion des étudiants
INSERT INTO etudiant (nom, prenom, date_naissance) VALUES
('Martin', 'Jean', '2000-05-15'),
('Dubois', 'Marie', '2001-08-22'),
('Bernard', 'Pierre', '2000-12-03'),
('Thomas', 'Sophie', '2001-03-18'),
('Robert', 'Lucas', '2000-07-29'),
('Richard', 'Emma', '2001-11-14'),
('Petit', 'Hugo', '2000-02-09'),
('Durand', 'Léa', '2001-06-25'),
('Leroy', 'Nathan', '2000-09-30'),
('Moreau', 'Chloé', '2001-04-12');

-- Insertion des semestres (L1, L2, L3)
INSERT INTO semestre (libelle, annee) VALUES
('Semestre 1', 'L1'),
('Semestre 2', 'L1'),
('Semestre 3', 'L2'),
('Semestre 4', 'L2'),
('Semestre 5', 'L3'),
('Semestre 6', 'L3');

-- Insertion des sessions d'examen
INSERT INTO session (libelle, date_session) VALUES
('Session principale S1', '2024-01-15'),
('Session rattrapage S1', '2024-02-20'),
('Session principale S2', '2024-06-10'),
('Session rattrapage S2', '2024-07-05'),
('Session principale S3', '2025-01-12'),
('Session rattrapage S3', '2025-02-18'),
('Session principale S4', '2025-06-09'),
('Session rattrapage S4', '2025-07-04'),
('Session principale S5', '2026-01-11'),
('Session rattrapage S5', '2026-02-16'),
('Session principale S6', '2026-06-08'),
('Session rattrapage S6', '2026-07-03');

-- Insertion des mentions de résultat
INSERT INTO mention_resultat (libelle, moyenne_min, moyenne_max) VALUES
('Très bien', 16, 20),
('Bien', 14, 15),
('Assez bien', 12, 13),
('Passable', 10, 11),
('Insuffisant', 0, 9);

-- Insertion des groupes de matières
INSERT INTO groupe_matiere (libelle) VALUES
('Obligatoires'),
('Langues et communication'),
('Projets et méthodologie'),
('Spécialisation Développement'),
('Spécialisation Data Science'),
('Spécialisation Réseaux'),
('Options transversales'),
('Projets avancés et stage');

-- Insertion des années scolaires
INSERT INTO annee_scolaire (annee1, annee2) VALUES
('2023-09-01', '2024-08-31'),
('2024-09-01', '2025-08-31'),
('2025-09-01', '2026-08-31');

-- Insertion des unités d'enseignement
INSERT INTO unite_enseignement (code_matiere, intitule, credits, groupe_id, semestre_id) VALUES
-- L1 Semestre 1 (Tous obligatoires)
('INF101', 'Algorithmique', 6, 1, 1),
('MAT101', 'Mathématiques discrètes', 5, 1, 1),
('ANG101', 'Anglais technique', 3, 2, 1),
('COM101', 'Communication', 2, 2, 1),
('PRO101', 'Projet initiation', 4, 3, 1),

-- L1 Semestre 2 (Tous obligatoires)
('INF102', 'Programmation Java', 6, 1, 2),
('MAT102', 'Algèbre linéaire', 5, 1, 2),
('WEB102', 'Développement web', 4, 1, 2),
('ESP102', 'Espagnol', 3, 2, 2),
('PRO102', 'Projet applicatif', 4, 3, 2),

-- L2 Semestre 3 (Tous obligatoires)
('INF201', 'Bases de données', 6, 1, 3),
('MAT201', 'Probabilités', 5, 1, 3),
('RES201', 'Réseaux', 4, 1, 3),
('ANG201', 'Anglais avancé', 3, 2, 3),
('PRO201', 'Projet tutoré', 4, 3, 3),

-- L2 Semestre 4 (Avec options)
-- Tronc commun obligatoire
('INF202', 'Architecture logicielle', 5, 1, 4),
('GES202', 'Gestion de projet', 3, 1, 4),

-- Options de spécialisation (1 groupe à choisir parmi 4, 5, 6)
('WEB202', 'Développement web avancé', 4, 4, 4),
('MOB202', 'Développement mobile', 4, 4, 4),
('DEV202', 'Patterns de conception', 4, 4, 4),

('STA202', 'Statistiques avancées', 4, 5, 4),
('ML202', 'Machine Learning', 4, 5, 4),
('VIS202', 'Visualisation données', 4, 5, 4),

('SEC202', 'Sécurité réseaux', 4, 6, 4),
('CLO202', 'Cloud computing', 4, 6, 4),
('ADM202', 'Admin systèmes', 4, 6, 4),

-- Options transversales
('ALL202', 'Allemand', 3, 7, 4),
('DRO202', 'Droit numérique', 3, 7, 4),

-- Projet obligatoire
('PPE202', 'Projet personnel encadré', 6, 8, 4),

-- L3 Semestre 5
('IA301', 'Intelligence artificielle', 6, 1, 5),
('SEC301', 'Cybersécurité', 5, 1, 5),
('PRO301', 'Projet avancé', 6, 8, 5),
('OPT301', 'Option libre', 4, 7, 5),

-- L3 Semestre 6
('ARCH302', 'Architecture cloud', 6, 1, 6),
('GES302', 'Management', 4, 1, 6),
('STAGE302', 'Stage professionnel', 12, 8, 6);

-- Insertion des inscriptions
INSERT INTO inscription (date_inscription, annee_scolaire_id, semestre_id, etudiant_id) VALUES
-- L1 S1
('2023-09-05', 1, 1, 1),
('2023-09-05', 1, 1, 2),
('2023-09-06', 1, 1, 3),
('2023-09-06', 1, 1, 4),
('2023-09-07', 1, 1, 5),
('2023-09-07', 1, 1, 6),

-- L1 S2
('2024-01-08', 1, 2, 1),
('2024-01-08', 1, 2, 2),
('2024-01-09', 1, 2, 3),
('2024-01-09', 1, 2, 4),
('2024-01-10', 1, 2, 5),
('2024-01-10', 1, 2, 6),

-- L2 S3
('2024-09-02', 2, 3, 1),
('2024-09-02', 2, 3, 2),
('2024-09-03', 2, 3, 3),
('2024-09-03', 2, 3, 4),
('2024-09-04', 2, 3, 5),
('2024-09-04', 2, 3, 6),

-- L2 S4
('2025-01-06', 2, 4, 1),
('2025-01-06', 2, 4, 2),
('2025-01-07', 2, 4, 3),
('2025-01-07', 2, 4, 4),
('2025-01-08', 2, 4, 5),
('2025-01-08', 2, 4, 6),

-- L3 S5
('2025-09-01', 3, 5, 1),
('2025-09-01', 3, 5, 2),
('2025-09-02', 3, 5, 3),
('2025-09-02', 3, 5, 4),
('2025-09-03', 3, 5, 5),

-- L3 S6
('2026-01-05', 3, 6, 1),
('2026-01-05', 3, 6, 2),
('2026-01-06', 3, 6, 3),
('2026-01-06', 3, 6, 4),
('2026-01-07', 3, 6, 5);

-- Insertion des notes
INSERT INTO notes (valeur, inscription_id, session_id, unite_id) VALUES
-- Notes S1
(15, 1, 1, 1), (14, 1, 1, 2), (16, 1, 1, 3), (13, 1, 1, 4), (15, 1, 1, 5),
(12, 2, 1, 1), (11, 2, 1, 2), (14, 2, 1, 3), (15, 2, 1, 4), (13, 2, 1, 5),
(18, 3, 1, 1), (17, 3, 1, 2), (16, 3, 1, 3), (15, 3, 1, 4), (17, 3, 1, 5),

-- Notes S2
(14, 7, 3, 6), (13, 7, 3, 7), (15, 7, 3, 8), (12, 7, 3, 9), (16, 7, 3, 10),
(16, 8, 3, 6), (15, 8, 3, 7), (14, 8, 3, 8), (13, 8, 3, 9), (17, 8, 3, 10),

-- Notes S3
(14, 13, 5, 11), (15, 13, 5, 12), (16, 13, 5, 13), (17, 13, 5, 14), (15, 13, 5, 15),
(13, 14, 5, 11), (14, 14, 5, 12), (15, 14, 5, 13), (16, 14, 5, 14), (14, 14, 5, 15),

-- Notes S4 (avec options différentes)
-- Étudiant 1 - Spécialisation Développement
(14, 19, 7, 16), (15, 19, 7, 17), (16, 19, 7, 18), (12, 19, 7, 19), (13, 19, 7, 20), (17, 19, 7, 25),
-- Étudiant 2 - Spécialisation Data Science
(16, 20, 7, 21), (15, 20, 7, 22), (14, 20, 7, 23), (11, 20, 7, 16), (14, 20, 7, 17), (16, 20, 7, 25),
-- Étudiant 3 - Spécialisation Réseaux
(13, 21, 7, 24), (14, 21, 7, 25), (12, 21, 7, 26), (15, 21, 7, 16), (16, 21, 7, 17), (15, 21, 7, 27),

-- Notes S5
(15, 25, 9, 28), (16, 25, 9, 29), (14, 25, 9, 30), (17, 25, 9, 31),
(14, 26, 9, 28), (15, 26, 9, 29), (16, 26, 9, 30), (15, 26, 9, 31),

-- Notes S6
(16, 30, 11, 32), (15, 30, 11, 33), (17, 30, 11, 34),
(15, 31, 11, 32), (16, 31, 11, 33), (18, 31, 11, 34);

INSERT INTO users (username, password) VALUES ('admin', 'admin');