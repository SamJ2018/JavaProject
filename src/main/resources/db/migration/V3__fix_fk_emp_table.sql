alter table tbl_emp drop foreign key fk_emp_dept;

alter table tbl_emp
	add constraint fk_emp_dept
		foreign key (d_id) references tbl_dept (dept_id);

