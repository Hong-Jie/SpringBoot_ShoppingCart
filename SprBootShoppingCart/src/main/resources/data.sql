create table ACCOUNTS
(
  USER_NAME VARCHAR(20) not null,
  ACTIVE    BIT not null,
  PASSWORD  VARCHAR(128) not null,
  USER_ROLE VARCHAR(20) not null
) ;
 
alter table ACCOUNTS
  add primary key (USER_NAME) ;
 
create table PRODUCTS
(
  CODE        VARCHAR(20) not null,
  IMAGE_PATH  VARCHAR(1024) not null,
  NAME        VARCHAR(255) not null,
  PRICE       double precision not null,
  CREATE_DATE datetime not null
) ;
 
alter table PRODUCTS
  add primary key (CODE) ;

create table ORDERS
(
  ID               VARCHAR(50) not null,
  AMOUNT           double precision not null,
  CUSTOMER_ADDRESS VARCHAR(255) not null,
  CUSTOMER_EMAIL   VARCHAR(128) not null,
  CUSTOMER_NAME    VARCHAR(255) not null,
  CUSTOMER_PHONE   VARCHAR(128) not null,
  ORDER_DATE       datetime not null,
  ORDER_NUM        INTEGER not null
) ;
alter table ORDERS
  add primary key (ID) ;
alter table ORDERS
  add constraint ORDER_UK unique (ORDER_NUM) ;

create table ORDER_DETAILS
(
  ID         VARCHAR(50) not null,
  AMOUNT     double precision not null,
  PRICE      double precision not null,
  QUANTITY   INTEGER not null,
  ORDER_ID   VARCHAR(50) not null,
  PRODUCT_ID VARCHAR(20) not null
) ;
  
alter table ORDER_DETAILS
  add primary key (ID) ;
alter table ORDER_DETAILS
  add constraint ORDER_DETAIL_ORD_FK foreign key (ORDER_ID)
  references ORDERS (ID);
alter table ORDER_DETAILS
  add constraint ORDER_DETAIL_PROD_FK foreign key (PRODUCT_ID)
  references PRODUCTS (CODE);
 

insert into ACCOUNTS (USER_NAME, ACTIVE, PASSWORD, USER_ROLE)
values ('employee1', 1,
'$2a$10$T/WRB3tUdz9tYLsmiVZmAe8KpLk8K.mppLzj3PtWssgeTxhgNdoku', 'ROLE_EMPLOYEE');
 
insert into ACCOUNTS (USER_NAME, ACTIVE, PASSWORD, USER_ROLE)
values ('manager1', 1,
'$2a$10$UaPHRGoiLqUxVobE8mPVw.fyDCkNy8/ZKgcAbG2wYvy4uTlajGo5m', 'ROLE_MANAGER');

set @img_path = "/images";

insert into PRODUCTS (CODE, IMAGE_PATH, NAME, PRICE, CREATE_DATE)
values ('P01', CONCAT(@img_path,"/","apple",".png"), 'apple', 5, CURDATE());
 
insert into PRODUCTS (CODE, IMAGE_PATH, NAME, PRICE, CREATE_DATE)
values ('P02', CONCAT(@img_path,"/","banana",".png"), 'banana', 3, CURDATE());
 
insert into PRODUCTS (CODE, IMAGE_PATH, NAME, PRICE, CREATE_DATE)
values ('P03', CONCAT(@img_path,"/","durian",".png"), 'durian', 50, CURDATE());
 
insert into PRODUCTS (CODE, IMAGE_PATH, NAME, PRICE, CREATE_DATE)
values ('P04', CONCAT(@img_path,"/","mango",".png"), 'mango', 20, CURDATE());
 
insert into PRODUCTS (CODE, IMAGE_PATH, NAME, PRICE, CREATE_DATE)
values ('P05', CONCAT(@img_path,"/","cherry",".png"), 'cherry', 30, CURDATE());