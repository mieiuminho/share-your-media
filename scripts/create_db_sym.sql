DROP DATABASE IF EXISTS ShareYourMedia;
CREATE DATABASE ShareYourMedia;

USE ShareYourMedia;

CREATE TABLE REGULAR_USERS
(
    email    char(255) NOT NULL,
    name     char(255) NOT NULL,
    password char(88)  NOT NULL,
    salt     char(8)   NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE ADMIN_USERS
(
    email    char(255) NOT NULL,
    name     char(255) NOT NULL,
    password char(88)  NOT NULL,
    salt     char(8)   NOT NULL,
    PRIMARY KEY (email)
);

show tables;
