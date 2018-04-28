DROP DATABASE IF EXISTS ag;
CREATE DATABASE ag;
USE ag;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users_creds;
DROP TABLE IF EXISTS credentials;

CREATE TABLE users (
 ID SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 USERNAME VARCHAR (50),
 PASSWORD VARCHAR(50),
 EMAIL VARCHAR(50),
 PRIMARY KEY (ID)
);
INSERT INTO users VALUES 
(null, 'User#1', 'PASSWORD#1', 'user1@busy.minds.com'),
(null, 'User#2', 'PASSWORD#2', 'user1@busy.minds.com'),
(null, 'User#3', 'PASSWORD#3', 'user1@busy.minds.com'),
(null, 'User#4', 'PASSWORD#4', 'user1@busy.minds.com'),
(null, 'User#5', 'PASSWORD#4', 'user1@busy.minds.com'); 

CREATE TABLE credentials (
 ID SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 NAME VARCHAR (50),
 DESCRIPTION TEXT,
 PRIMARY KEY (ID)
);

INSERT INTO credentials VALUES
( null, 'Floor 1', 'Grants access to the first floor'),
( null, 'Floor 1', 'Grants access to the first floor');

CREATE TABLE userscred (
ID SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
USER_ID SMALLINT UNSIGNED NOT NULL,
CREDENTIAL_ID SMALLINT UNSIGNED NOT NULL,
PRIMARY KEY(ID),
FOREIGN KEY (USER_ID) REFERENCES users(ID),
FOREIGN KEY (CREDENTIAL_ID) REFERENCES credentials(ID)
);