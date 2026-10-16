TRUNCATE TABLE reservation, room, app_user RESTART IDENTITY CASCADE;

INSERT INTO app_user (name, card_uid) VALUES
    ('Jan Novak', 'CARD_001'),
    ('Petr Svoboda', 'CARD_002'),
    ('Eva Dvorakova', 'CARD_003')
;

INSERT INTO room (name) VALUES
    ('Lab 101'),
    ('Lab 102'),
    ('Lab 103')
;

INSERT INTO reservation (user_id, room_id, status_code, start_at, end_at, checked_in_at, checked_out_at) VALUES
    (1, 1, 'OVERSTAY', '2026-10-19 08:00:00', '2026-10-19 09:00:00', '2026-10-19 08:02:00', '2026-10-19 08:58:00'),
    (2, 1, 'ACTIVE', '2026-10-19 09:00:00', '2026-10-19 10:00:00', '2026-10-19 09:01:00', '2026-10-19 09:55:00'),
    (3, 2, 'GHOST', '2026-10-19 09:00:00', '2026-10-19 10:00:00', NULL, NULL),
    (1, 3, 'PENDING', '2026-10-19 11:00:00', '2026-10-19 12:00:00', NULL, NULL),
    (2, 2, 'ACTIVE', '2026-10-20 08:00:00', '2026-10-20 09:00:00', '2026-10-20 08:04:00', NULL),
    (1, 1, 'GHOST', '2026-10-20 10:00:00', '2026-10-20 11:00:00', NULL, NULL),
    (3, 1, 'PENDING', '2026-10-20 12:00:00', '2026-10-20 13:00:00', NULL, NULL),
    (2, 3, 'CONFIRMED', '2026-10-20 14:00:00', '2026-10-20 15:00:00', NULL, NULL)
;
