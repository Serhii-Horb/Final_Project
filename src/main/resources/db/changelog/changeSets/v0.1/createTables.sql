-- liquibase formatted sql

-- changeset Serhii:create_table_users
CREATE TABLE Users
(
    UserID       BIGINT AUTO_INCREMENT          NOT NULL,
    Name         VARCHAR(255)                   NULL,
    Email        VARCHAR(255)                   NULL,
    PhoneNumber  VARCHAR(255)                   NULL,
    PasswordHash VARCHAR(255)                   NULL,
    RefreshToken VARCHAR(255)                   NULL,
    Role         ENUM ('USER', 'ADMINISTRATOR') NULL,
    CONSTRAINT PK_USERS PRIMARY KEY (UserID)
);

-- changeset Daniil:create_table_products
CREATE TABLE Products
(
    ProductID     BIGINT AUTO_INCREMENT NOT NULL,
    Name          VARCHAR(255)          NULL,
    Description   VARCHAR(255)          NULL,
    Price         DECIMAL(10, 2)        NULL,
    CategoryID    BIGINT                NULL,
    ImageURL      VARCHAR(255)          NULL,
    DiscountPrice DECIMAL(10, 2)        NULL,
    CreatedAt     datetime              NULL,
    UpdatedAt     datetime              NULL,
    CONSTRAINT PK_PRODUCTS PRIMARY KEY (ProductID)
);

-- changeset Daniil:create_table_categories
CREATE TABLE Categories
(
    CategoryID BIGINT AUTO_INCREMENT NOT NULL,
    Name       VARCHAR(255)          NULL,
    CONSTRAINT PK_CATEGORIES PRIMARY KEY (CategoryID)
);

-- changeset Serhii:create_table_cart
CREATE TABLE Cart
(
    CartID BIGINT AUTO_INCREMENT NOT NULL,
    UserID BIGINT                NULL,
    CONSTRAINT PK_CART PRIMARY KEY (CartID),
    UNIQUE (UserID)
);

-- changeset Marat:create_table_cartitems
CREATE TABLE CartItems
(
    CartItemID BIGINT AUTO_INCREMENT NOT NULL,
    CartID     BIGINT                NULL,
    ProductID  BIGINT                NULL,
    Quantity   INT                   NULL,
    CONSTRAINT PK_CARTITEMS PRIMARY KEY (CartItemID)
);

-- changeset Serhii:create_table_favorites
CREATE TABLE Favorites
(
    FavoriteID BIGINT AUTO_INCREMENT NOT NULL,
    ProductID  BIGINT                NULL,
    UserID     BIGINT                NULL,
    CONSTRAINT PK_FAVORITES PRIMARY KEY (FavoriteID)
);

-- changeset Artemii:create_table_orders
CREATE TABLE Orders
(
    OrderID         BIGINT AUTO_INCREMENT                                                        NOT NULL,
    UserID          BIGINT                                                                       NULL,
    CreatedAt       datetime                                                                     NULL,
    DeliveryAddress VARCHAR(255)                                                                 NULL,
    ContactPhone    VARCHAR(255)                                                                 NULL,
    DeliveryMethod  ENUM ('COURIER_DELIVERY', 'SELF_DELIVERY' ,'DEPARTMENT_DELIVERY')            NULL,
    Status          ENUM ('CREATED','CANCEL', 'WAIT_PAYMENT', 'PAID', 'ON_THE_WAY', 'DELIVERED') NULL,
    UpdatedAt       datetime                                                                     NULL,
    CONSTRAINT PK_ORDERS PRIMARY KEY (OrderID)
);

-- changeset Artemii:create_table_orderitems
CREATE TABLE OrderItems
(
    OrderItemID     BIGINT AUTO_INCREMENT NOT NULL,
    OrderID         BIGINT                NULL,
    ProductID       BIGINT                NULL,
    Quantity        INT                   NULL,
    PriceAtPurchase DECIMAL(10, 2)        NULL,
    CONSTRAINT PK_ORDERITEMS PRIMARY KEY (OrderItemID)
);


-- changeset Daniil:create_foreign_key_products_categories
ALTER TABLE Products
    ADD CONSTRAINT foreign_key_products_categories FOREIGN KEY (CategoryID) REFERENCES
        Categories (CategoryID) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Serhii:create_foreign_key_favorites_users
ALTER TABLE Favorites
    ADD CONSTRAINT foreign_key_favorites_users FOREIGN KEY (UserID) REFERENCES
        Users (UserID) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Serhii:create_foreign_key_favorites_products
ALTER TABLE Favorites
    ADD CONSTRAINT foreign_key_favorites_products FOREIGN KEY (ProductID) REFERENCES
        Products (ProductID) ON UPDATE CASCADE ON DELETE SET NULL;

-- changeset Serhii:create_foreign_key_cart_users
ALTER TABLE Cart
    ADD CONSTRAINT foreign_key_cart_users FOREIGN KEY (UserID) REFERENCES
        Users (UserID) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Marat:create_foreign_key_cartitems_cart
ALTER TABLE CartItems
    ADD CONSTRAINT foreign_key_cartitems_cart FOREIGN KEY (CartID) REFERENCES
        Cart (CartID) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Marat:create_foreign_key_cartitems_products
ALTER TABLE CartItems
    ADD CONSTRAINT foreign_key_cartitems_products FOREIGN KEY (ProductID) REFERENCES
        Products (ProductID) ON UPDATE CASCADE ON DELETE SET NULL;

-- changeset Artemii:create_foreign_key_orders_users
ALTER TABLE Orders
    ADD CONSTRAINT foreign_key_orders_users FOREIGN KEY (UserID) REFERENCES
        Users (UserID) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Artemii:create_foreign_key_orderitems_orders
ALTER TABLE OrderItems
    ADD CONSTRAINT foreign_key_orderitems_orders FOREIGN KEY (OrderID) REFERENCES
        Orders (OrderID) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Artemii:create_foreign_key_orderitems_products
ALTER TABLE OrderItems
    ADD CONSTRAINT foreign_key_orderitems_products FOREIGN KEY (ProductID) REFERENCES
        Products (ProductID) ON UPDATE CASCADE ON DELETE SET NULL;


-- changeset Daniil:create_index_products_to_categories
CREATE INDEX foreign_key_products_categories ON Products (CategoryID);

-- changeset Serhii:create_index_favorites_to_users
CREATE INDEX foreign_key_favorites_users ON Favorites (UserID);

-- changeset Serhii:create_index_favorites_to_products
CREATE INDEX foreign_key_favorites_products ON Favorites (ProductID);

-- changeset Serhii:create_index_cart
CREATE INDEX foreign_key_cart_users ON Cart (UserID);

-- changeset Marat:create_index_cartitems_to_cart
CREATE INDEX foreign_key_cartitems_cart ON CartItems (CartID);

-- changeset Marat:create_index_cartitems_to_products
CREATE INDEX foreign_key_cartitems_products ON CartItems (ProductID);

-- changeset Artemii:create_index_orders
CREATE INDEX foreign_key_orders_users ON Orders (UserID);

-- changeset Artemii:create_index_orderitems_to_orders
CREATE INDEX foreign_key_orderitems_orders ON OrderItems (OrderID);

-- changeset Artemii:create_index_orderitems_to_products
CREATE INDEX foreign_key_orderitems_products ON OrderItems (ProductID);