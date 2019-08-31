package com.cys.crud.test;

import com.cys.crud.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * @author sam
 * @apiNote
 * @since 2019-08-31-10:32
 **/
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MvcTest {
    //虚拟mvc请求，获取处理结果
    MockMvc mockMvc;

    //传入SpringMvc的ioc
    @Autowired
    WebApplicationContext context;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pageNo", "5"))
                .andReturn();
        //请求成功，取出请求域中的pageInfo测试
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码:" + pi.getPageNum());
        System.out.println("总页码:" + pi.getPages());
        System.out.println("总记录数:" + pi.getTotal());
        System.out.println("在页面需要连续显示的页码：");
        int[] nums = pi.getNavigatepageNums();
        for (int num : nums) {
            System.out.println("" + num);
        }
        //获取员工数据
        List<Employee> employees = pi.getList();
        for (Employee employee : employees) {
            System.out.println("ID:" + employee.getdId() + " Name:" + employee.getEmpName());
        }
    }
}
