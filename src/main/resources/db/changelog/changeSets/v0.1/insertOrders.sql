-- liquibase formatted sql

-- changeset artemii:insert_orders
INSERT INTO orders(UserId,CreatedAt,DeliveryAddress,ContactPhone,DeliveryMethod,Status,UpdatedAt)
VALUES
    (2,NOW(),'museum 25','+4030201','Courier','ORDERED',NOW()),
    (1,NOW(),'front 10','+403431','Courier','ORDERED',NOW()),
    (2,NOW(),'museum 25','+4030201','Courier','ORDERED',NOW()),
    (3,NOW(),'indep 12','+450403','Courier','ORDERED',NOW());