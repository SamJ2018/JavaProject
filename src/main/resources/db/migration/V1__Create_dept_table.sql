create table tbl_dept
(
	dept_id int auto_increment,
	dept_name varchar(255) not null,
	constraint tbl_dept_pk
		primary key (dept_id)
);