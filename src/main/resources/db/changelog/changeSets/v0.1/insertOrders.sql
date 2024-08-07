-- liquibase formatted sql

-- changeset artemii:insert_orders
INSERT INTO Orders(UserId, CreatedAt, DeliveryAddress, ContactPhone, DeliveryMethod, Status, UpdatedAt)
VALUES (1, '2024-06-25 12:00:00', '123 Main St', '555-1234', 'COURIER_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (2, '2024-06-25 12:00:00', '456 Oak St', '555-5678', 'SELF_DELIVERY', 'CANCELED', '2024-06-25 12:00:00'),
       (3, '2024-06-25 12:00:00', '789 Pine St', '555-9101', 'DEPARTMENT_DELIVERY', 'AWAITING_PAYMENT',
        '2024-06-25 12:00:00'),
       (4, '2024-06-25 12:00:00', '321 Birch St', '555-1122', 'SELF_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (5, '2024-06-25 12:00:00', '654 Cedar St', '555-3344', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (6, '2024-06-25 12:00:00', '987 Elm St', '555-5566', 'DEPARTMENT_DELIVERY', 'DELIVERED', '2024-06-25 12:00:00'),
       (7, '2024-06-25 12:00:00', '135 Maple St', '555-7788', 'DEPARTMENT_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (8, '2024-06-25 12:00:00', '246 Spruce St', '555-9900', 'SELF_DELIVERY', 'AWAITING_PAYMENT', '2024-06-25 12:00:00'),
       (9, '2024-06-25 12:00:00', '357 Willow St', '555-2233', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (10, '2024-06-25 12:00:00', '468 Ash St', '555-4455', 'DEPARTMENT_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (11, '2024-06-25 12:00:00', '579 Beech St', '555-6677', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (12, '2024-06-25 12:00:00', '680 Fir St', '555-8899', 'DEPARTMENT_DELIVERY', 'CANCELED', '2024-06-25 12:00:00'),
       (13, '2024-06-25 12:00:00', '791 Pine St', '555-1011', 'SELF_DELIVERY', 'AWAITING_PAYMENT', '2024-06-25 12:00:00'),
       (14, '2024-06-25 12:00:00', '902 Oak St', '555-1213', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (15, '2024-06-25 12:00:00', '123 Birch St', '555-1415', 'DEPARTMENT_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (16, '2024-06-25 12:00:00', '234 Cedar St', '555-1617', 'DEPARTMENT_DELIVERY', 'CANCELED', '2024-06-25 12:00:00'),
       (17, '2024-06-25 12:00:00', '345 Elm St', '555-1819', 'DEPARTMENT_DELIVERY', 'AWAITING_PAYMENT',
        '2024-06-25 12:00:00'),
       (18, '2024-06-25 12:00:00', '456 Maple St', '555-2021', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (19, '2024-06-25 12:00:00', '567 Spruce St', '555-2223', 'DEPARTMENT_DELIVERY', 'CREATED',
        '2024-06-25 12:00:00'),
       (20, '2024-06-25 12:00:00', '678 Willow St', '555-2425', 'DEPARTMENT_DELIVERY', 'AWAITING_PAYMENT',
        '2024-06-25 12:00:00'),
       (21, '2024-06-25 12:00:00', '789 Ash St', '555-2627', 'SELF_DELIVERY', 'ON_THE_WAY', '2024-06-25 12:00:00'),
       (22, '2024-06-25 12:00:00', '890 Beech St', '555-2829', 'DEPARTMENT_DELIVERY', 'AWAITING_PAYMENT',
        '2024-06-25 12:00:00'),
       (23, '2024-06-25 12:00:00', '901 Fir St', '555-3031', 'DEPARTMENT_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (24, '2024-06-25 12:00:00', '123 Pine St', '555-3233', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (25, '2024-06-25 12:00:00', '234 Oak St', '555-3435', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (26, '2024-06-25 12:00:00', '345 Birch St', '555-3637', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (27, '2024-06-25 12:00:00', '456 Cedar St', '555-3839', 'SELF_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (28, '2024-06-25 12:00:00', '567 Elm St', '555-4041', 'SELF_DELIVERY', 'CANCELED', '2024-03-25 12:00:00'),
       (29, '2024-06-25 12:00:00', '678 Maple St', '555-4243', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (30, '2024-06-25 12:00:00', '789 Spruce St', '555-4445', 'DEPARTMENT_DELIVERY', 'AWAITING_PAYMENT',
        '2024-06-25 12:00:00'),
       (31, '2024-06-25 12:00:00', '890 Willow St', '555-4647', 'SELF_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (32, '2024-06-25 12:00:00', '901 Ash St', '555-4849', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (33, '2024-06-25 12:00:00', '123 Beech St', '555-5051', 'SELF_DELIVERY', 'CANCELED', '2024-06-25 12:00:00'),
       (34, '2024-06-25 12:00:00', '234 Fir St', '555-5253', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (35, '2024-06-25 12:00:00', '345 Pine St', '555-5455', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (36, '2024-06-25 12:00:00', '456 Oak St', '555-5657', 'SELF_DELIVERY', 'CREATED', '2024-06-25 12:00:00'),
       (37, '2024-06-25 12:00:00', '567 Birch St', '555-5859', 'DEPARTMENT_DELIVERY', 'AWAITING_PAYMENT',
        '2024-06-25 12:00:00'),
       (38, '2024-06-25 12:00:00', '678 Cedar St', '555-6061', 'DEPARTMENT_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (39, '2024-06-25 12:00:00', '789 Elm St', '555-6263', 'SELF_DELIVERY', 'ON_THE_WAY', '2024-06-25 12:00:00'),
       (40, '2024-06-25 12:00:00', '890 Maple St', '555-6465', 'DEPARTMENT_DELIVERY', 'CANCELED', '2024-06-25 12:00:00'),
       (41, '2024-06-25 12:00:00', '901 Spruce St', '555-6667', 'DEPARTMENT_DELIVERY', 'ON_THE_WAY',
        '2024-06-25 12:00:00'),
       (42, '2024-06-25 12:00:00', 'Berlin, Germany', '+49 30 1234567', 'COURIER_DELIVERY', 'CREATED',
        '2024-06-25 12:00:00'),
       (43, '2024-06-21 12:00:00', 'Paris, France', '+33 1 2345678', 'COURIER_DELIVERY', 'PAID',
        '2024-06-25 12:00:00'),
       (44, '2024-06-25 12:00:00', 'Rome, Italy', '+39 06 3456789', 'COURIER_DELIVERY', 'PAID', '2024-06-25 12:00:00'),
       (45, '2024-06-25 12:00:00', 'Madrid, Spain', '+34 91 4567890', 'COURIER_DELIVERY', 'ON_THE_WAY',
        '2024-06-22 12:00:00'),
       (46, '2024-06-25 12:00:00', 'Athens, Greece', '+30 21 5678901', 'COURIER_DELIVERY', 'DELIVERED',
        '2024-06-21 12:00:00'),
       (47, '2024-06-25 12:00:00', 'Vienna, Austria', '+43 1 6789012', 'SELF_DELIVERY', 'CANCELED',
        '2024-06-25 12:00:00'),
       (48, '2023-06-25 12:00:00', 'Amsterdam, Netherlands', '+31 20 7890123', 'COURIER_DELIVERY', 'CREATED',
        '2024-06-25 12:00:00'),
       (49, '2024-06-25 12:00:00', 'Brussels, Belgium', '+32 2 8901234', 'SELF_DELIVERY', 'PAID',
        '2024-06-25 12:00:00'),
       (50, '2024-06-25 12:00:00', 'Stockholm, Sweden', '+46 8 1234567', 'SELF_DELIVERY', 'ON_THE_WAY',
        '2024-04-25 12:00:00');