-- liquibase formatted sql

-- changeset serhii:create_table_products
CREATE TABLE products (CategoryId INT NULL, DiscountPrice DECIMAL NULL, Price DECIMAL NULL,
                       ProductId INT AUTO_INCREMENT NOT NULL, CreatedAt datetime NULL,
                       UpdatedAt datetime NULL, Quantity INT NULL, Description VARCHAR(255) NULL,
                       ImageURL VARCHAR(255) NULL, Name VARCHAR(255) NULL,
                       CONSTRAINT PK_PRODUCTS PRIMARY KEY (ProductId));

-- changeset serhii:create_table_categories
CREATE TABLE categories (CategoryId INT AUTO_INCREMENT NOT NULL,
                         Name VARCHAR(255) NULL, CONSTRAINT
                             PK_CATEGORIES PRIMARY KEY (CategoryId));

-- changeset serhii:create_table_users
CREATE TABLE users (userId INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NULL, email VARCHAR(255) NULL,
                    phoneNumber VARCHAR(255) NULL, passwordHash VARCHAR(255) NULL,
                    role ENUM('USER', 'ADMINISTRATOR') NULL, CONSTRAINT PK_USERS PRIMARY KEY (userId));

-- changeset serhii:create_table_cart
CREATE TABLE cart (cartId INT AUTO_INCREMENT NOT NULL,
                   userId INT NULL, CONSTRAINT PK_CART PRIMARY KEY (cartId), UNIQUE (userId));


-- changeset serhii:create_foreign_key_products_categories
ALTER TABLE products ADD CONSTRAINT foreign_key_products_categories FOREIGN KEY (CategoryId)
    REFERENCES categories (CategoryId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset serhii:create_foreign_key_cart_users
ALTER TABLE cart ADD CONSTRAINT foreign_key_cart_users FOREIGN KEY (userId) REFERENCES users (userId)
    ON UPDATE RESTRICT ON DELETE RESTRICT;


-- changeset serhii:create_index_products
CREATE INDEX foreign_key_products_categories ON products(CategoryId);

-- changeset serhii:create_index_cart
CREATE INDEX foreign_key_cart_users ON cart(userId);