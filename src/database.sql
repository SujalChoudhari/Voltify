# run this before running application drop database voltify;
create database voltify;
use voltify;
CREATE TABLE meter
(
   id INT,
   username TEXT,
   user_password TEXT,
   created_at DATE,
   place TEXT,
   city TEXT,
   pincode INT,
   administrator BOOL
);
CREATE TABLE bill
(
   id INT,
   meter_id INT,
   created_at DATE,
   units FLOAT,
   amount FLOAT,
   paid BOOL
);
INSERT INTO meter VALUES
(
   1,
   'admin',
   'admin',
   '2023-07-10',
   null,
   null,
   null,
   true
);
TRUNCATE meter;
TRUNCATE bill;
SELECT *
FROM
   meter;
SELECT *
FROM
   bill;