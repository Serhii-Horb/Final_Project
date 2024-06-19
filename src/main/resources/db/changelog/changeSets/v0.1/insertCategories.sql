-- liquibase formatted sql

-- changeset serhii:insert_categories

INSERT INTO categories (Name)
VALUES ('Beer'), ('Wine'), ('Distilled Spirits'), ('Liqueurs'), ('Cider and Perry');