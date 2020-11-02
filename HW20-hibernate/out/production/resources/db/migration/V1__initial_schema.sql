create table users (id bigint not null, name varchar(255) not null, addressDataSet_id bigint, primary key (id));
create table address (id bigint not null, street varchar(255), primary key (id));
create table phones (id bigint not null, number varchar(255) not null, user_id bigint not null, primary key (id));
create sequence hibernate_sequence start with 1 increment by 1;