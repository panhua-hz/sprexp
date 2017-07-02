create table user(
	username varchar(30) unique not null,
	password varchar(255) not null
);

insert into user (username, password) values ('u001','abcde');
