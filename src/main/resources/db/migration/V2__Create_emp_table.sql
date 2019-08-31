create table tbl_emp
(
	emp_id int auto_increment,
	emp_name varchar(255) not null,
	gender varchar(10) not null,
	email varchar(255) not null,
	d_id int not null,
	constraint tbl_emp_pk
		primary key (emp_id),
	constraint fk_emp_dept
		foreign key (emp_id) references tbl_dept (dept_id)
);

