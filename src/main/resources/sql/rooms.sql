INSERT INTO rooms (id, room_name, room_capacity,available)
VALUES
    -- Floor 1
    (1, 'Zeus', 3, true),
    (2, 'Hera', 4, true),
    (3, 'Poseidon', 3, true),
    (4, 'Demeter', 3, true),
    (5, 'Athena', 3, true),

    -- Floor 2
    (6, 'Apollo', 3, true),
    (7, 'Artemis', 3, true),
    (8, 'Ares', 3, true),
    (9, 'Aphrodite', 5, true),
    (10, 'Hephaestus', 3, true),

    -- Floor 3
    (11, 'Hermes', 4, true),
    (12, 'Dionysus', 3, true),
    (13, 'Hades', 3, true),
    (14, 'Persephone', 3, true),
    (15, 'Hestia', 3, true);

ALTER TABLE rooms AUTO_INCREMENT = 16;