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

-- changeset artemii:create_table_orders
CREATE TABLE orders (OrderId INT AUTO_INCREMENT NOT NULL, UserId INT NULL, CreatedAt datetime NULL,
                     DeliveryAddress VARCHAR(255) NULL, ContactPhone VARCHAR(255) NULL, DeliveryMethod VARCHAR(255) NULL,
                     Status ENUM('ORDERED','PAID','ON_THE_WAY','PENDING_PAYMENT','DELIVERED','CANCELLED') NULL,
                     UpdatedAt datetime NULL,CONSTRAINT PK_ORDERS PRIMARY KEY (OrderId));

-- changeset artemii:create_table_orderitems
CREATE TABLE orderitems (OrderItemId INT AUTO_INCREMENT NOT NULL, OrderId INT NULL, ProductId INT NULL,
                         Quantity INT NULL, PriceAtPurchase DECIMAL NULL, CONSTRAINT PK_ORDERITEMS PRIMARY KEY (OrderItemId));


-- changeset serhii:create_index_products
CREATE INDEX foreign_key_products_categories ON products(CategoryId);


CREATE INDEX foreign_key_cartItems_products ON cartItems(ProductId);

CREATE INDEX foreign_key_cartItems_cart ON cartItems(CartId);


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


ALTER TABLE cartItems ADD CONSTRAINT foreign_key_cartItems_products FOREIGN KEY (ProductId)
    REFERENCES products (ProductId) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE cartItems ADD CONSTRAINT foreign_key_cartItems_cart FOREIGN KEY (CartId)
   REFERENCES cart (CartId) ON UPDATE RESTRICT ON DELETE RESTRICT;

---- changeset artemii:create_foreign_key_orders_users
--ALTER TABLE orders ADD CONSTRAINT foreign_key_orders_users FOREIGN KEY (UserId)
--   REFERENCES users (UserId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset artemii:create_foreign_key_orderitems_orders
ALTER TABLE orderitems ADD CONSTRAINT foreign_key_orderitems_orders FOREIGN KEY (OrderId)
   REFERENCES orders (OrderId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset artemii:create_foreign_key_orderitems_products
ALTER TABLE orderitems ADD CONSTRAINT foreign_key_orderitems_products FOREIGN KEY (ProductId)
   REFERENCES products (ProductId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset serhii:create_foreign_key_cart_users
ALTER TABLE cart ADD CONSTRAINT foreign_key_cart_users FOREIGN KEY (userId) REFERENCES users (userId)
    ON UPDATE RESTRICT ON DELETE RESTRICT;


-- changeset serhii:create_index_products
CREATE INDEX foreign_key_products_categories ON products(CategoryId);

-- changeset serhii:create_index_cart
CREATE INDEX foreign_key_cart_users ON cart(userId);

