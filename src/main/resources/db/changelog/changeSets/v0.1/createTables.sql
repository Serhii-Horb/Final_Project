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

-- changeset serhii:create_index_products
CREATE INDEX foreign_key_products_categories ON products(CategoryId);

-- changeset serhii:create_foreign_key_products_categories
ALTER TABLE products ADD CONSTRAINT foreign_key_products_categories FOREIGN KEY (CategoryId)
    REFERENCES categories (CategoryId) ON UPDATE RESTRICT ON DELETE RESTRICT;