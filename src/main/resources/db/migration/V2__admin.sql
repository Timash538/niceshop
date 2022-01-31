insert into usr (id, active, username, email, password) values ('1', 'true', 'admin', 'admin@gmail.com', '$2a$10$e/OeBlPlQn0gPjdoiQAsveXc8ZgERWbfdHq/boe.quoQRugomwuSC');
insert into user_role (user_id, roles) values (1, 'ADMIN');
insert into user_role (user_id, roles) values (1, 'USER');