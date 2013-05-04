##�����
create table category(id int primary key not null auto_increment,name varchar(64) not null, parent_id int not null,gmt_create DATETIME not null, gmt_modified DATETIME not null);

##���±�
create table post(id int primary key not null auto_increment,category_id int,parent_category_id int, title varchar(128) not null, content text, url varchar(128),gmt_create DATETIME not null, gmt_modified DATETIME not null, author varchar(32), view int, comment int);

##���۱�
create table comments(id int primary key not null auto_increment, author varchar(64), content text not null, post_id int not null,gmt_create DATETIME not null, gmt_modified DATETIME not null, status int);
          
##�û���
create table user(id int primary key not null auto_increment, username varchar(64), password varchar(128));
