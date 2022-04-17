drop database ecommerce_db;
create database ecommerce_db;


use ecommerce_db;


-- @block
create table customer (
    cust_id INTEGER,
    cust_name VARCHAR(20),
    mobile VARCHAR(15),
    cust_email VARCHAR(30),
    cust_password VARCHAR(30),
    constraint pk_customer PRIMARY KEY (cust_id)
);


-- @block
create table online_order (
    order_id INTEGER,
    cust_id INTEGER,
    price FLOAT,
    order_location VARCHAR(50),
    dispatched BOOLEAN,
    constraint pk_order PRIMARY KEY (order_id)
);


-- @block
create table transaction (
    transction_id INTEGER,
    order_id INTEGER,
    cust_id INTEGER,
    transaction_status VARCHAR(10),
    constraint pk_transaction PRIMARY KEY (transction_id)
);


-- @block
create table product (
    product_id INTEGER,
    order_id INTEGER,
    seller VARCHAR(40),
    price FLOAT,
    product_name VARCHAR(20),
    category VARCHAR(20),
    constraint pk_product PRIMARY KEY (product_id)
);