create table accounts
(
  user_name VARCHAR(20) not null,
  active    BIT not null,
  password  VARCHAR(128) not null,
  user_role VARCHAR(20) not null
) ;
 
alter table accounts
  add primary key (user_name) ;
 
create table products
(
  code        VARCHAR(20) not null,
  image_path  VARCHAR(1024) not null,
  name        VARCHAR(255) not null,
  price       double precision not null,
  create_date datetime not null
) ;
 
alter table products
  add primary key (code) ;

create table orders
(
  id               VARCHAR(50) not null,
  amount           double precision not null,
  customer_address VARCHAR(255) not null,
  customer_email   VARCHAR(128) not null,
  customer_name    VARCHAR(255) not null,
  customer_phone   VARCHAR(128) not null,
  order_date       datetime not null,
  order_num        INTEGER not null
) ;
alter table orders
  add primary key (id) ;
alter table orders
  add constraint ORDER_UK unique (order_num) ;

create table order_details
(
  id         VARCHAR(50) not null,
  amount     double precision not null,
  price      double precision not null,
  quantity   INTEGER not null,
  order_id   VARCHAR(50) not null,
  product_id VARCHAR(20) not null
) ;
  
alter table order_details
  add primary key (id) ;
alter table order_details
  add constraint ORDER_DETAIL_ORD_FK foreign key (order_id)
  references orders (id);
alter table order_details
  add constraint ORDER_DETAIL_PROD_FK foreign key (product_id)
  references products (code);
 

insert into accounts (user_name, active, password, user_role)
values ('employee1', 1,
'$2a$10$T/WRB3tUdz9tYLsmiVZmAe8KpLk8K.mppLzj3PtWssgeTxhgNdoku', 'ROLE_EMPLOYEE');
 
insert into accounts (user_name, active, password, user_role)
values ('manager1', 1,
'$2a$10$UaPHRGoiLqUxVobE8mPVw.fyDCkNy8/ZKgcAbG2wYvy4uTlajGo5m', 'ROLE_MANAGER');

set @img_path = "/images";

insert into products (code, image_path, name, price, create_date)
values ('P01', CONCAT(@img_path,"/","apple",".png"), 'apple', 5, CURDATE());
 
insert into products (code, image_path, name, price, create_date)
values ('P02', CONCAT(@img_path,"/","banana",".png"), 'banana', 3, CURDATE());
 
insert into products (code, image_path, name, price, create_date)
values ('P03', CONCAT(@img_path,"/","durian",".png"), 'durian', 50, CURDATE());
 
insert into products (code, image_path, name, price, create_date)
values ('P04', CONCAT(@img_path,"/","mango",".png"), 'mango', 20, CURDATE());
 
insert into products (code, image_path, name, price, create_date)
values ('P05', CONCAT(@img_path,"/","cherry",".png"), 'cherry', 30, CURDATE());