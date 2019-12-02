DROP DATABASE IF EXISTS ShareYourMedia;
CREATE DATABASE ShareYourMedia;

USE ShareYourMedia;

CREATE TABLE REGULAR_USERS
(
    email    char(255) NOT NULL,
    name     char(255) NOT NULL,
    password char(88)  NOT NULL,
    salt     char(8)   NOT NULL,
    PRIMARY KEY (email),
    UNIQUE (email),
    INDEX (email)
);

CREATE TABLE ADMIN_USERS
(
    email    char(255) NOT NULL,
    name     char(255) NOT NULL,
    password char(88)  NOT NULL,
    salt     char(8)   NOT NULL,
    PRIMARY KEY (email),
    UNIQUE (email),
    INDEX (email)
);

drop table MEDIAFILES;
CREATE TABLE MEDIAFILES
(
    name   char(255) NOT NULL,
    artist char(255) NOT NULL,
    PRIMARY KEY (name, artist)
);

show tables;
