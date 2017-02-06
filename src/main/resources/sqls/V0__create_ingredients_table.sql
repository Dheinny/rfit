CREATE TABLE IF NOT EXISTS ingredients (
    id                    NUMERIC NOT NULL,
    name                  VARCHAR,
    type                  VARCHAR,
    qtt_per_portion       NUMERIC,
    cal_count             NUMERIC,
    PRIMARY KEY (id)
);

CREATE SEQUENCE ingredients_seq;