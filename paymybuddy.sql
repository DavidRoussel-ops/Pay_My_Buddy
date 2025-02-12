
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE paymybuddy;

USE paymybuddy;

CREATE TABLE USER (
ID int PRIMARY KEY AUTO_INCREMENT NOT NULL,
USERNAME VARCHAR(100) NOT NULL,
EMAIL VARCHAR(255) NOT NULL,
PASSWORD VARCHAR(255) NOT NULL
);

CREATE TABLE TRANSACTION (
ID int PRIMARY KEY AUTO_INCREMENT NOT NULL,
DESCRIPTION VARCHAR(255) NOT NULL,
AMOUNT DOUBLE NOT NULL
);

CREATE TABLE USER_FRIENDS (
ID int PRIMARY KEY AUTO_INCREMENT NOT NULL,
USER_FRIEND_ID int NULL,
FOREIGN KEY (USER_FRIEND_ID)
REFERENCES USER(ID));

CREATE TABLE USER_TRANSACTION_RECEIVER (
ID int PRIMARY KEY AUTO_INCREMENT NOT NULL,
USER_ID int NULL,
TRANSACTION_ID int NULL,
FOREIGN KEY(USER_ID)
REFERENCES USER(ID),
FOREIGN KEY(TRANSACTION_ID)
REFERENCES TRANSACTION(ID));

CREATE TABLE USER_TRANSACTION_SENDER (
ID int PRIMARY KEY AUTO_INCREMENT NOT NULL,
USER_ID int NULL,
TRANSACTION_ID int ,
FOREIGN KEY(USER_ID)
REFERENCES USER(ID),
FOREIGN KEY(TRANSACTION_ID)
REFERENCES TRANSACTION(ID));

INSERT INTO USER(ID,USERNAME,EMAIL,PASSWORD) VALUES(1,'Jaques', 'jaquesatgmail.com', 'jaquespassword');
INSERT INTO USER(ID,USERNAME,EMAIL,PASSWORD) VALUES(2,'Pierre', 'pierreatgmail.com', 'pierrepassword');
INSERT INTO USER(ID,USERNAME,EMAIL,PASSWORD) VALUES(3,'Jean', 'jeanatgmail.com', 'jeanpassword');
INSERT INTO USER(ID,USERNAME,EMAIL,PASSWORD) VALUES(4,'Michel', 'michelatgmail.com', 'michelpassword');

INSERT INTO TRANSACTION(ID,DESCRIPTION, AMOUNT) VALUES(1,'restaurant', 25);
INSERT INTO TRANSACTION(ID,DESCRIPTION, AMOUNT) VALUES(2,'magasin', 30);
INSERT INTO TRANSACTION(ID,DESCRIPTION, AMOUNT) VALUES(3,'note de frais', 50);

COMMIT;