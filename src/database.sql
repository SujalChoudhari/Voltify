create database voltify;
use voltify;
CREATE TABLE meter (
    id INT,
    username TEXT,
    created_at DATE,
    place TEXT,
    city TEXT,
    pincode INT
);

# using meter itself as a admin account, those id will be different than a regular user.
alter table meter add administrator bool; 
 
CREATE TABLE bill (
    id INT,
    meter_id INT,
    created_at DATE,
    units FLOAT,
    amount FLOAT,
    paid BOOL
);

truncate meter;
truncate bill;

select * from meter;
select * from bill;