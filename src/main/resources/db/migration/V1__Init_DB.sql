


create
 sequence hibernate_sequence start 2 increment 1;

create table
 user_role
 (user_id int8 not null,
  roles varchar(255));

create table
 usr
 (id int8 not null,
  active boolean not null,
  email varchar(50) not null,
  firstname varchar(30),
  lastname varchar(30),
  password varchar(255) not null,
  username varchar(30) not null,
  primary key (id));

alter table
 if exists usr
  add constraint UK_g9l96r670qkidthshajdtxrqf
  unique (email);

alter table
 if exists usr
 add constraint UK_dfui7gxngrgwn9ewee3ogtgym
 unique (username);

alter table
 if exists user_role
 add constraint FKfpm8swft53ulq2hl11yplpr5
 foreign key (user_id)
 references usr;