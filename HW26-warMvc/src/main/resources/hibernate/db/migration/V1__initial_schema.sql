create table phones (id bigint not null, number varchar(255) not null, user_id bigint not null, primary key (id));
create table users (id bigint not null, login varchar(255), name varchar(255) not null, password varchar(255), addressDataSet_id bigint, primary key (id));
create table address (id bigint not null, street varchar(255), primary key (id));
create sequence hibernate_sequence start with 20 increment by 1;