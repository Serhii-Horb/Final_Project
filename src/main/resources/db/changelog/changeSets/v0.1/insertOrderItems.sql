-- liquibase formatted sql

-- changeset artemii:insert_orderitems
INSERT INTO orderitems (OrderId,ProductId,Quantity,PriceAtPurchase)
VALUES
        (1,3,2,240.5),
        (2,1,1,160.0),
        (2,3,2,240.5),
        (3,2,5,400.1);