-- INSERT INTO semestre (libelle, annee) VALUES
-- ('Semestre 1', 'L1'),
-- ('Semestre 2', 'L1'),
-- ('Semestre 3', 'L2'),
-- ('Semestre 4', 'L2'),
-- ('Semestre 5', 'L3'),
-- ('Semestre 6', 'L3');

-- ---Table option
-- INSERT INTO "option" (libelle, semestre_id) VALUES
-- ('Connaissances Scientifiques et Techniques de Base', 1), -- id 1
-- ('Connaissances Scientifiques et Techniques de Base', 2), -- id 2
-- ('Connaissances Scientifiques et Techniques de Base', 3), -- id 3

-- ('Développement', 4), -- id 4
-- ('Développement', 5), -- id 5
-- ('Développement', 6), -- id 6

-- ('Bases de Données et Réseaux', 4), -- id 7
-- ('Bases de Données et Réseaux', 5), -- id 8
-- ('Bases de Données et Réseaux', 6), -- id 9

-- ('Web et Design', 4), -- id 10
-- ('Web et Design', 5), -- id 11
-- ('Web et Design', 6); -- id 12



-- -- Correction de la table groupe_matiere
-- INSERT INTO groupe_matiere (libelle) VALUES
-- ('Obligatoire'),
-- ('S4-Dev-G1'),('S4-Dev-G2'),
-- ('S4-BDR-G1'),('S4-BDR-G2'),
-- ('S4-Web-G1'),('S4-Web-G2'),
-- ('S6-Dev-G1'),('S6-BDR-G1'),('S6-Web-G1');




-- ---Semestre 1
-- INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
-- ('INF101', 'Programmation procédurale'),
-- ('INF104', 'HTML et Introduction au Web'),
-- ('INF107', 'Informatique de Base'),
-- ('MTH101', 'Arithmétique et nombres'),
-- ('MTH102', 'Analyse mathématique'),
-- ('ORG101', 'Techniques de communication');

-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (1, 1, 7, 1),
-- (1, 2, 5, 1),
-- (1, 3, 4, 1),
-- (1, 4, 4, 1),
-- (1, 5, 6, 1),
-- (1, 6, 4, 1);

-- ---Semestre 2
-- INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
-- ('INF102', 'Bases de données relationnelles'),
-- ('INF103', 'Bases de l’administration système'),
-- ('INF105', 'Maintenance matériel et logiciel'),
-- ('INF106', 'Compléments de programmation'),
-- ('MTH103', 'Calcul Vectoriel et Matriciel'),
-- ('MTH105', 'Probabilité et Statistique');

-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (2, 7, 5, 1),
-- (2, 8, 5, 1),
-- (2, 9, 4, 1),
-- (2, 10, 6, 1),
-- (2, 11, 6, 1),
-- (2, 12, 4, 1);

-- ---Semestre 3
-- INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
-- ('INF201', 'Programmation orientée objet'),
-- ('INF202', 'Bases de données objets'),
-- ('INF203', 'Programmation système'),
-- ('INF208', 'Réseaux informatiques'),
-- ('MTH201', 'Méthodes numériques'),
-- ('ORG201', 'Bases de gestion');

-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (3, 13, 6, 1),
-- (3, 14, 6, 1),
-- (3, 15, 4, 1),
-- (3, 16, 6, 1),
-- (3, 17, 4, 1),
-- (3, 18, 4, 1);



-- ---Unite d'enseignement des options Semestre 4,5,6
-- INSERT INTO unite_enseignement (code_matiere, intitule) VALUES
-- ('INF204','Système d’information géographique'),
-- ('INF205','Système d’information'),
-- ('INF206','Interface Homme/Machine'),
-- ('INF207','Éléments d’algorithmique'),
-- ('INF210','Mini-projet de développement'),
-- ('MTH204','Géométrie'),
-- ('MTH205','Équations différentielles'),
-- ('MTH206','Optimisation'),
-- ('MTH203','MAO'),
-- ('INF301','Architecture logicielle'),
-- ('INF304','Développement pour mobiles'),
-- ('INF307','Conception en modèle orienté objet'),
-- ('ORG301','Gestion d’entreprise'),
-- ('ORG302','Gestion de projets'),
-- ('ORG303','Anglais pour les affaires'),
-- ('INF310','Codage'),
-- ('INF313','Programmation avancée, frameworks'),
-- ('INF302','Technologies d’accès aux réseaux'),
-- ('INF303','Multimédia'),
-- ('INF316','Projet de développement'),
-- ('ORG304','Communication d’entreprise'),
-- ('INF305','Gestion de domaines, clustering'),
-- ('INF308','Conception en modèle relationnel'),
-- ('INF311','Datawarehouse et datamining'),
-- ('INF314','Bases de données avancées'),
-- ('INF317','Projet BDD & réseaux'),
-- ('INF209','Web dynamique'),
-- ('INF212','Mini-projet Web et design'),
-- ('MTH202','Analyse des données'),
-- ('INF306','Introduction au cloud'),
-- ('INF309','Web avancé'),
-- ('INF312','Web design'),
-- ('INF315','Web services'),
-- ('INF318','Projet de Web & design');


-- ---Parcours développement Semestre 4,5,6
-- -- Semestre 4 (option_id 4)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (4, 1, 6, 2),
-- (4, 2, 6, 2),
-- (4, 3, 6, 3),
-- (4, 4, 6, 3),
-- (4, 5, 10, 1),

-- (4, 6, 4, 2),
-- (4, 7, 4, 2),
-- (4, 8, 4, 3),
-- (4, 9, 4, 3);



-- -- Semestre 5 (option_id 5)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (5, 10, 6, 1),
-- (5, 11, 6, 1),
-- (5, 12, 6, 1),
-- (5, 13, 5, 1),
-- (5, 14, 4, 1),
-- (5, 15, 3, 1);



-- -- Semestre 6 (option_id 6)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (6, 16, 4, 1),
-- (6, 17, 6, 1),

-- (6, 18, 6, 8),
-- (6, 19, 6, 8),
-- (6, 20, 10, 8),

-- (6, 21, 4, 1);



-- ---Parcours Bases de Données et Réseaux Semestre 4,5,6
-- -- Semestre 4 (option_id 7)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (7, 2, 6, 1),

-- (7, 1, 6, 4),
-- (7, 3, 6, 4),
-- (7, 4, 6, 5),
-- (7, 22, 10, 5),

-- (7, 29, 4, 4),
-- (7, 7, 4, 4),
-- (7, 8, 4, 5),
-- (7, 9, 4, 5);



-- -- Semestre 5 (option_id 8)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (8, 18, 6, 1),
-- (8, 22, 6, 1),
-- (8, 23, 6, 1),
-- (8, 13, 5, 1),
-- (8, 14, 4, 1),
-- (8, 15, 3, 1);



-- -- Semestre 6 (option_id 9)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (9, 24, 4, 1),
-- (9, 25, 6, 1),

-- (9, 19, 6, 9),
-- (9, 11, 6, 9),
-- (9, 26, 10, 9),

-- (9, 21, 4, 1);



-- ---Parcours Web et Design Semestre 4,5,6
-- -- Semestre 4 (option_id 10)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (10, 1, 6, 6),
-- (10, 2, 6, 6),
-- (10, 3, 6, 7),
-- (10, 27, 6, 7),
-- (10, 28, 10, 1),

-- (10, 29, 4, 6),
-- (10, 6, 4, 6),
-- (10, 8, 4, 7),
-- (10, 9, 4, 7);



-- -- Semestre 5 (option_id 11)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (11, 19, 6, 1),
-- (11, 30, 6, 1),
-- (11, 31, 6, 1),
-- (11, 13, 5, 1),
-- (11, 14, 4, 1),
-- (11, 15, 3, 1);



-- -- Semestre 6 (option_id 12)
-- INSERT INTO option_unite_enseignement (option_id, unite_enseignement_id, credits, groupe_id) VALUES
-- (12, 32, 4, 1),
-- (12, 33, 6, 1),

-- (12, 18, 6, 10),
-- (12, 11, 6, 10),
-- (12, 34, 10, 10),

-- (12, 21, 4, 1);


