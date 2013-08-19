##分类表
create table category(id int primary key not null auto_increment,name varchar(64) not null, parent_id int not null,gmt_create DATETIME not null, gmt_modified DATETIME not null);

##文章表
create table post(id int primary key not null auto_increment,category_id int,parent_category_id int, title varchar(128) not null, content text, url varchar(128),gmt_create DATETIME not null, gmt_modified DATETIME not null, author varchar(32), view int, comment int);

##评论表
create table comments(id int primary key not null auto_increment, author varchar(64), content text not null, post_id int not null,gmt_create DATETIME not null, gmt_modified DATETIME not null, status int);
          
##用户表
create table user(id int primary key not null auto_increment, username varchar(64), password varchar(128));


##分享链接
create table geeklink(id int primary key not null auto_increment, url varchar(128), title varchar(128),author varchar(32), gmt_create DATETIME not null, gmt_modified DATETIME not null);


##个人定位记录表
create table lbsinfo(id int primary key not null auto_increment,gmt_create DATETIME not null, gmt_modified DATETIME not null, username varchar(64) not null,lat varchar(64)  not null, lng varchar(64)  not null,lbs_time DATETIME not null, lbstype varchar(32) not null);