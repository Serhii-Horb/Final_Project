-- liquibase formatted sql

-- changeset serhii:insert_products

INSERT INTO products (Name, Description, Price, CategoryId, ImageURL, DiscountPrice,  CreatedAt, UpdatedAt, Quantity)
VALUES
    ('IPA Beer', 'A hoppy IPA beer with citrus flavors.', '6.99', 1, 'https://example.com/images/ipa_beer.jpg', '5.99', NOW(), NOW(), 1),
    ('Red Wine', 'A full-bodied red wine with notes of blackberry and oak.', '19.99', 2, 'https://example.com/images/red_wine.jpg', '17.99', NOW(), NOW(), 2),
    ('Whiskey', 'A smooth whiskey aged 12 years in oak barrels.', '39.99', 3, 'https://example.com/images/whiskey.jpg', '35.99', NOW(), NOW(), 3),
    ('Coffee Liqueur', 'A rich coffee liqueur perfect for cocktails.', '24.99', 4, 'https://example.com/images/coffee_liqueur.jpg', '22.99', NOW(), NOW(), 4),
    ('Apple Cider', 'A crisp and refreshing apple cider.', '9.99', 5, 'https://example.com/images/apple_cider.jpg', '8.99', NOW(), NOW(), 5);
