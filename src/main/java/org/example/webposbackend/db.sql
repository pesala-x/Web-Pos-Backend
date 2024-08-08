create database pos;
use pos;
create table customer(
                         id varchar(50) primary key,
                         name varchar(50),
                         address varchar(100),
                         mobile varchar(20)
);

CREATE TABLE items (
                       code VARCHAR(255) NOT NULL PRIMARY KEY,
                       description VARCHAR(255) NOT NULL,
                       price DOUBLE NOT NULL,
                       qty INT NOT NULL
);
