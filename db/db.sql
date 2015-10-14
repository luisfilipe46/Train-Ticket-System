DROP DATABASE IF EXISTS tickets;
CREATE DATABASE tickets;
USE tickets;
DROP TABLE IF EXISTS credit_cards;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS train_travels;


CREATE TABLE credit_cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type INT NOT NULL,
    number INT NOT NULL,
    validity DATE,    
    created DATETIME,
    modified DATETIME    
);

CREATE TABLE users (
    -- id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    credit_card_id INT NOT NULL,
    FOREIGN KEY credit_card_key (credit_card_id) REFERENCES credit_cards(id),

    created DATETIME,
    modified DATETIME
);

CREATE TABLE train_travels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,

    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    lotation INT NOT NULL,
    ocupied_seats INT NOT NULL DEFAULT 0,
    
    created DATETIME,
    modified DATETIME
);

