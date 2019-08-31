package com.cys.crud.service;

import com.cys.crud.bean.Employee;
import com.cys.crud.bean.EmployeeExample;
import com.cys.crud.bean.Msg;
import com.cys.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author sam
 * @apiNote
 * @since 2019-08-31-10:17
 **/
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查出所有员工
     *
     * @return
     */
    public List<Employee> getAll() {
        //分页逻辑在controller层
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 员工保存
     *
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 检验用户名是否可用
     *
     * @param empName
     * @return ture:可用  false不可用
     */
    public boolean checkUser(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmpNameEqualTo(empName);

        long count = employeeMapper.countByExample(employeeExample);
        return count == 0;
    }

    /**
     * 按照员工id查询员工
     *
     * @param id
     * @return
     */
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 员工更新
     *
     * @param employee
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 员工删除
     * @param id
     */
    public void deleteEmp(Integer id) {
        // TODO Auto-generated method stub
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        // TODO Auto-generated method stub
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
}
