-- liquibase formatted sql

-- changeset Serhii:insert_favorites
insert into favorites (productId, userId)
values
    (1, 1),
    (2, 2),
    (3, 3),
    (3, 4),
    (1, 5),
    (2, 6),
    (1, 7),
    (1, 8),
    (3, 8),
    (2, 10);