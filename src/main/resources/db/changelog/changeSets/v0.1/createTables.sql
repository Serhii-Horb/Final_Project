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

CREATE TABLE cartItems (CartItemId INT AUTO_INCREMENT NOT NULL, CartId INT NULL, ProductId INT NULL,
                        Quantity INT NULL, CONSTRAINT PK_CART_ITEMS PRIMARY KEY (CartItemId));

CREATE TABLE cart (CartId INT AUTO_INCREMENT NOT NULL, UserId INT NULL, CONSTRAINT PK_CART PRIMARY KEY (CartId));

-- changeset serhii:create_index_products
CREATE INDEX foreign_key_products_categories ON products(CategoryId);

CREATE INDEX foreign_key_cartItems_products ON cartItems(ProductId);

CREATE INDEX foreign_key_cartItems_cart ON cartItems(CartId);





-- changeset serhii:create_foreign_key_products_categories
ALTER TABLE products ADD CONSTRAINT foreign_key_products_categories FOREIGN KEY (CategoryId)
    REFERENCES categories (CategoryId) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE cartItems ADD CONSTRAINT foreign_key_cartItems_products FOREIGN KEY (ProductId)
    REFERENCES products (ProductId) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE cartItems ADD CONSTRAINT foreign_key_cartItems_cart FOREIGN KEY (CartId)
   REFERENCES cart (CartId) ON UPDATE RESTRICT ON DELETE RESTRICT;