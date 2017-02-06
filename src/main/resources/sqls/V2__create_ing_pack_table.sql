CREATE TABLE IF NOT EXISTS ing_pack (
    id_ing                  NUMERIC NOT NULL REFERENCES ingredients ON DELETE RESTRICT,
    id_pack                 NUMERIC NOT NULL REFERENCES packed_meals ON DELETE CASCADE,
    qtt_ing                 NUMERIC,
    PRIMARY KEY (id_ing, id_pack)
);