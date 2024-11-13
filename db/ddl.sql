-- Creazione della tabella tier_list
CREATE TABLE tier_list (
                           id SERIAL PRIMARY KEY,             -- Chiave primaria incrementale
                           tier_name VARCHAR(50) NOT NULL,    -- Nome del tier
                           tier_description TEXT,             -- Descrizione del tier
                           tier_grades TEXT[]                 -- Array di stringhe per i possibili gradi del tier
);

-- Creazione della tabella elementi
CREATE TABLE elementi (
                          id SERIAL PRIMARY KEY,             -- Chiave primaria incrementale
                          nome VARCHAR(100) NOT NULL,        -- Nome dell'elemento
                          grade VARCHAR(10),                 -- Grado dell'elemento
                          source VARCHAR(100)                -- Fonte dell'elemento
);

-- Creazione della tabella di relazione elemento_tierlist
CREATE TABLE elemento_tierlist (
                                   elemento_id INTEGER,               -- Chiave esterna che si riferisce a 'id' di 'elementi'
                                   tierlist_id INTEGER,               -- Chiave esterna che si riferisce a 'id' di 'tier_list',

                                   PRIMARY KEY (elemento_id, tierlist_id), -- Chiave primaria composta

                                   CONSTRAINT fk_elemento FOREIGN KEY (elemento_id)
                                       REFERENCES elementi (id)       -- Definisce la relazione con 'elementi'
                                       ON DELETE CASCADE,             -- Cancellazione a cascata

                                   CONSTRAINT fk_tierlist FOREIGN KEY (tierlist_id)
                                       REFERENCES tier_list (id)      -- Definisce la relazione con 'tier_list'
                                       ON DELETE CASCADE              -- Cancellazione a cascata
);

-- Esempio di inserimento in tier_list
INSERT INTO tier_list (tier_name, tier_description, tier_grades)
VALUES
    ('S', 'Top tier', ARRAY['S+', 'S', 'S-']),
    ('A', 'High tier', ARRAY['A+', 'A', 'A-']),
    ('B', 'Mid tier', ARRAY['B+', 'B', 'B-']);

-- Esempio di inserimento in elementi
INSERT INTO elementi (nome, grade, source)
VALUES
    ('Elemento1', 'A', 'Fonte1'),
    ('Elemento2', 'S', 'Fonte2');

-- Esempio di inserimento in elemento_tierlist per collegare elementi a tier_list
INSERT INTO elemento_tierlist (elemento_id, tierlist_id)
VALUES
    (1, 1),  -- Collega Elemento1 al tier S
    (2, 1),  -- Collega Elemento2 al tier S
    (1, 2);  -- Collega Elemento1 al tier A
