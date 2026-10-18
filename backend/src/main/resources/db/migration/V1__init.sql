CREATE table status (
	code text PRIMARY KEY
);

INSERT INTO status (code) VALUES
	('PENDING'), ('ACTIVE'), ('GHOST'),
	('CONFIRMED'), ('OVERSTAY'), ('COMPLETED')
;

CREATE TABLE app_user (
	id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name text NOT NULL,
	card_uid text UNIQUE
);

CREATE TABLE room (
	id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name text NOT NULL UNIQUE,
	is_active boolean DEFAULT true
);

CREATE TABLE reservation (
	id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	user_id integer NOT NULL REFERENCES app_user(id),
	room_id integer NOT NULL REFERENCES room(id),
	status_code text NOT NULL REFERENCES status(code) DEFAULT 'PENDING',
	checked_in_at  timestamptz,
	checked_out_at timestamptz,
	start_at       timestamptz NOT NULL,
        end_at         timestamptz NOT NULL,
        CHECK (end_at > start_at)

);
