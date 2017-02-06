CREATE TABLE IF NOT EXISTS packed_meals (
    id                    NUMERIC NOT NULL,
    name                  VARCHAR,
    category              VARCHAR,
    is_available          BOOLEAN,
    price                 NUMERIC,
    price_off             NUMERIC,
    PRIMARY KEY (id)
);

CREATE SEQUENCE packed_meals_seq;