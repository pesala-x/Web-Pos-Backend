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

CREATE TABLE orders (
                        order_id VARCHAR(50) PRIMARY KEY,
                        order_date DATE NOT NULL,
                        customer_id VARCHAR(50) NOT NULL,
                        total DECIMAL(10, 2) NOT NULL,
                        discount DECIMAL(5, 2) NOT NULL,
                        subtotal DECIMAL(10, 2) NOT NULL,
                        cash DECIMAL(10, 2) NOT NULL,
                        balance DECIMAL(10, 2) NOT NULL,
                        FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE order_items (
                             order_id VARCHAR(50),
                             item_code VARCHAR(50),
                             quantity INT NOT NULL,
                             price DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(order_id),
                             FOREIGN KEY (item_code) REFERENCES items(code)
);

