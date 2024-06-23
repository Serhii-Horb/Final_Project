-- liquibase formatted sql

-- changeset serhii:insert_users
insert into users (name, email, phoneNumber, passwordHash, role)
values
    ('Alice Smith', 'alice.smith@example.com', '1234567890', 'hashpassword1', 'USER'),
    ('Bob Johnson', 'bob.johnson@example.com', '1234567891', 'hashpassword2', 'ADMINISTRATOR'),
    ('Charlie Brown', 'charlie.brown@example.com', '1234567892', 'hashpassword3', 'USER'),
    ('David Wilson', 'david.wilson@example.com', '1234567893', 'hashpassword4', 'USER'),
    ('Emma Davis', 'emma.davis@example.com', '1234567894', 'hashpassword5', 'USER'),
    ('Frank Clark', 'frank.clark@example.com', '1234567895', 'hashpassword6', 'ADMINISTRATOR'),
    ('Grace Hall', 'grace.hall@example.com', '1234567896', 'hashpassword7', 'USER'),
    ('Henry Lewis', 'henry.lewis@example.com', '1234567897', 'hashpassword8', 'USER'),
    ('Isabel Young', 'isabel.young@example.com', '1234567898', 'hashpassword9', 'USER'),
    ('Jack Allen', 'jack.allen@example.com', '1234567899', 'hashpassword10', 'USER'),
    ('Karen King', 'karen.king@example.com', '1234567800', 'hashpassword11', 'USER'),
    ('Larry Wright', 'larry.wright@example.com', '1234567801', 'hashpassword12', 'USER'),
    ('Mona Scott', 'mona.scott@example.com', '1234567802', 'hashpassword13', 'USER'),
    ('Nina Green', 'nina.green@example.com', '1234567803', 'hashpassword14', 'USER'),
    ('Oscar Baker', 'oscar.baker@example.com', '1234567804', 'hashpassword15', 'USER'),
    ('Paula Adams', 'paula.adams@example.com', '1234567805', 'hashpassword16', 'USER'),
    ('Quincy Turner', 'quincy.turner@example.com', '1234567806', 'hashpassword17', 'USER'),
    ('Rachel Miller', 'rachel.miller@example.com', '1234567807', 'hashpassword18', 'USER'),
    ('Steve Carter', 'steve.carter@example.com', '1234567808', 'hashpassword19', 'USER'),
    ('Tina Martinez', 'tina.martinez@example.com', '1234567809', 'hashpassword20', 'USER');