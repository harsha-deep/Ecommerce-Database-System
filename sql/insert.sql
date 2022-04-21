-- @block
insert into customer 
values 
    (101, 'Mr. Bean', '111-111-111', 'j@email.com', '28n38y3'),
    (102, 'Teddy Daniels', '222-222-222', 'td@email.com', '7ui29jx'),
    (103, 'Tyler Durden', '444-444-444', 'ty@email.com', '32y8h8n32');

-- @block
insert into online_order 
values
    (1701, 101, 7893.23, 'London', TRUE),
    (1702, 102, 8979.65, 'Shutter Island', TRUE),
    (1703, 103, 3443.33, 'Wilmington', TRUE);

-- @block
insert into trans 
values 
    (10001, 1701, 101, 'Complete'),
    (10002, 1702, 102, 'Processing'),
    (10003, 1703, 103, 'Initiated');

-- @block
insert into product 
values 
    (201, 1701, 'GUND', 7893.23, 'Teddy', 'Toys'),
    (202, 1702, 'Bayer', 8979.65,'Aspirin', 'Medicines'),
    (203, 1703, 'Paper Street Soap Co.', 2099.97, 'F.Club', 'Soap');

-- @block
insert into owner
values
    (1, 'Omni-Man','777-777-777','om@email.com', '1234');