CREATE EXTENSION IF NOT EXISTS btree_gist;

ALTER TABLE reservation
    ADD CONSTRAINT no_overlap
    EXCLUDE USING gist (
       room_id WITH =,
       tstzrange(start_at, end_at) WITH &&
     )
     WHERE (status_code IN ('PENDING','CONFIRMED','ACTIVE','OVERSTAY'));
