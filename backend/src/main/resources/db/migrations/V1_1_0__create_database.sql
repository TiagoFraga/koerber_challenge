CREATE TABLE IF NOT EXISTS zone (

    id INTEGER NOT NULL,
    borough VARCHAR NOT NULL,
    service_zone VARCHAR NOT NULL,
    zone_name VARCHAR NOT NULL,

    CONSTRAINT zone_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trip (

    id INTEGER NOT NULL,
    vendor_id INTEGER NOT NULL,
    cab_type INTEGER NOT NULL,
    drop_off_date VARCHAR NOT NULL,
    pickup_date VARCHAR NOT NULL,
    drop_off_zone_id INTEGER NOT NULL,
    pickup_zone_id INTEGER NOT NULL,

    CONSTRAINT trip_pk PRIMARY KEY (id),
    CONSTRAINT drop_off_zone_id_fk FOREIGN KEY (drop_off_zone_id) REFERENCES zone (id),
    CONSTRAINT pickup_zone_id_fk FOREIGN KEY (pickup_zone_id) REFERENCES zone (id)
);