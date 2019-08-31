package com.cys.crud.controller;

import com.cys.crud.bean.Employee;
import com.cys.crud.bean.Msg;
import com.cys.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sam
 * @apiNote 处理员工CRUD
 * @since 2019-08-31-10:15
 **/
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    /**
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids")String ids){
        //批量删除
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合
            for (String string : str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }

    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable(value = "id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    @ResponseBody
    @RequestMapping("/checkUser")
    public Msg checkUser(String empName) {
        //先判断是否合法
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }
        boolean b = employeeService.checkUser(empName);
        return b ? Msg.success() : Msg.fail().add("va_msg", "用户名不可用");
    }

    @ResponseBody
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            //校验失败，返回失败,在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段" + fieldError.getField());
                System.out.println("错误的信息：" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * ResponseBody要能正常工作需要导入jackson包（注意！！）
     *
     * @param pageNo
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        //传入页码，以及每一页的大小
        PageHelper.startPage(pageNo, 5);
        //startPage后的查询是分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装emps，只要将pageInfo交给页面即可。pageInfo封装了详细的分页信息，包括查询出来的数据
        // 5 :连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 查询员工数据（分页查询） 废弃此方法
     *
     * @return
     */
//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, Model model) {
        //传入页码，以及每一页的大小
        PageHelper.startPage(pageNo, 5);
        //startPage后的查询是分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装emps，只要将pageInfo交给页面即可。pageInfo封装了详细的分页信息，包括查询出来的数据
        // 5 :连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }
}
