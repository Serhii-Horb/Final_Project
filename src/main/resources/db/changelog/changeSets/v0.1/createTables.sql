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

-- changeset serhii:create_foreign_key_products_categories
ALTER TABLE products ADD CONSTRAINT foreign_key_products_categories FOREIGN KEY (CategoryId)
    REFERENCES categories (CategoryId) ON UPDATE RESTRICT ON DELETE RESTRICT;

---- changeset artemii:create_foreign_key_orders_users
--ALTER TABLE orders ADD CONSTRAINT foreign_key_orders_users FOREIGN KEY (UserId)
--   REFERENCES users (UserId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset artemii:create_foreign_key_orderitems_orders
ALTER TABLE orderitems ADD CONSTRAINT foreign_key_orderitems_orders FOREIGN KEY (OrderId)
   REFERENCES orders (OrderId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset artemii:create_foreign_key_orderitems_products
ALTER TABLE orderitems ADD CONSTRAINT foreign_key_orderitems_products FOREIGN KEY (ProductId)
   REFERENCES products (ProductId) ON UPDATE RESTRICT ON DELETE RESTRICT;
