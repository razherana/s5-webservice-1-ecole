CREATE TABLE "etudiant" (
    "id" SERIAL PRIMARY KEY,
    "nom" VARCHAR(50) NOT NULL,
    "prenom" VARCHAR(50) NOT NULL,
    "date_naissance" DATE
);

CREATE TABLE "semestre" (
    "id" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL,
    "annee" VARCHAR(50) NOT NULL
);

CREATE TABLE "option" (
    "id" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL,
    "semestre_id" INTEGER NOT NULL
);

CREATE TABLE "session" (
    "id" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL,
    "date_session" DATE NOT NULL
);

CREATE TABLE "mention_resultat" (
    "id" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50) NOT NULL,
    "moyenne_min" INTEGER NOT NULL,
    "moyenne_max" INTEGER NOT NULL
);

CREATE TABLE "groupe_matiere" (
    "id" SERIAL PRIMARY KEY,
    "libelle" VARCHAR(50)
);

CREATE TABLE "annee_scolaire" (
    "id" SERIAL PRIMARY KEY,
    "annee1" DATE,
    "annee2" DATE NOT NULL
);

CREATE TABLE "unite_enseignement" (
    "id" SERIAL PRIMARY KEY,
    "code_matiere" VARCHAR(50) NOT NULL,
    "intitule" VARCHAR(50) NOT NULL
);

CREATE TABLE "option_unite_enseignement" (
    "id" SERIAL PRIMARY KEY,
    "option_id" INTEGER NOT NULL,
    "unite_enseignement_id" INTEGER NOT NULL,
    "credits" INTEGER NOT NULL,
    "groupe_id" INTEGER NOT NULL
);

CREATE TABLE "inscription" (
    "id" SERIAL PRIMARY KEY,
    "date_inscription" DATE NOT NULL,
    "annee_scolaire_id" INTEGER NOT NULL,
    "semestre_id" INTEGER NOT NULL,
    "etudiant_id" INTEGER NOT NULL
);

CREATE TABLE "notes" (
    "id" SERIAL PRIMARY KEY,
    "valeur" NUMERIC(5, 2) NOT NULL,
    "inscription_id" INTEGER NOT NULL,
    "session_id" INTEGER NOT NULL,
    "unite_id" INTEGER NOT NULL
);

CREATE TABLE "users" (
    "id" SERIAL PRIMARY KEY,
    "username" VARCHAR(50) UNIQUE NOT NULL,
    "password" VARCHAR(100) NOT NULL
);

ALTER TABLE "option"
ADD FOREIGN KEY ("semestre_id") REFERENCES "semestre" ("id");

ALTER TABLE "option_unite_enseignement"
ADD FOREIGN KEY ("groupe_id") REFERENCES "groupe_matiere" ("id");

ALTER TABLE "option_unite_enseignement"
ADD FOREIGN KEY ("option_id") REFERENCES "option" ("id");

ALTER TABLE "option_unite_enseignement"
ADD FOREIGN KEY ("unite_enseignement_id") REFERENCES "unite_enseignement" ("id");

ALTER TABLE "inscription"
ADD FOREIGN KEY ("annee_scolaire_id") REFERENCES "annee_scolaire" ("id");

ALTER TABLE "inscription"
ADD FOREIGN KEY ("semestre_id") REFERENCES "semestre" ("id");

ALTER TABLE "inscription"
ADD FOREIGN KEY ("etudiant_id") REFERENCES "etudiant" ("id");

ALTER TABLE "notes"
ADD FOREIGN KEY ("inscription_id") REFERENCES "inscription" ("id");

ALTER TABLE "notes"
ADD FOREIGN KEY ("session_id") REFERENCES "session" ("id");

ALTER TABLE "notes"
ADD FOREIGN KEY ("unite_id") REFERENCES "unite_enseignement" ("id");
