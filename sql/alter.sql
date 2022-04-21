-- @block
alter table online_order
add constraint fk_order FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


-- @block
alter table trans
add constraint trans FOREIGN KEY (order_id) REFERENCES online_order(order_id);


-- @block
alter table product
add constraint fk_product FOREIGN KEY (order_id) REFERENCES online_order(order_id);