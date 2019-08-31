package com.cys.crud.controller;

import com.cys.crud.bean.Department;
import com.cys.crud.bean.Msg;
import com.cys.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author sam
 * @apiNote 处理和部门有关的请求
 * @since 2019-08-31-16:22
 **/
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts() {
        //查出所有部门信息
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
