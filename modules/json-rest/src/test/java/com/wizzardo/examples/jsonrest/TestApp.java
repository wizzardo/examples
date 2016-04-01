package com.wizzardo.examples.jsonrest;

import com.wizzardo.examples.jsonrest.model.Employee;
import com.wizzardo.tools.http.Request;
import com.wizzardo.tools.json.JsonTools;
import com.wizzardo.tools.misc.With;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by wizzardo on 31.03.16.
 */
public class TestApp {

    String serverUrl = "http://localhost:8080/rest/employees";

    public Employee createEmployee() throws IOException {
        Employee employee = With.with(new Employee(), it -> {
            it.id = -1;
            it.name = "test employee";
            it.createdDate = new Date();
        });

        String response = new Request(serverUrl)
                .json(JsonTools.serialize(employee))
                .put()
                .asString();

        Employee responseEmployee = JsonTools.parse(response, Employee.class);

        Assert.assertTrue(responseEmployee.id >= 0);
        Assert.assertEquals(employee.name, responseEmployee.name);
        Assert.assertEquals(employee.createdDate, responseEmployee.createdDate);
        return responseEmployee;
    }

    @Test
    public void getEmployee() throws IOException {
        Employee employee = createEmployee();

        String response = new Request(serverUrl + "/" + employee.id)
                .get()
                .asString();

        Employee responseEmployee = JsonTools.parse(response, Employee.class);

        Assert.assertEquals(employee.id, responseEmployee.id);
        Assert.assertEquals(employee.name, responseEmployee.name);
        Assert.assertEquals(employee.createdDate, responseEmployee.createdDate);
    }

    @Test
    public void deleteEmployee() throws IOException {
        Employee employee = createEmployee();

        String response = new Request(serverUrl + "/" + employee.id)
                .delete()
                .asString();

        Employee responseEmployee = JsonTools.parse(response, Employee.class);

        Assert.assertEquals(employee.id, responseEmployee.id);
        Assert.assertEquals(employee.name, responseEmployee.name);
        Assert.assertEquals(employee.createdDate, responseEmployee.createdDate);

        response = new Request(serverUrl + "/" + employee.id)
                .get()
                .asString();

        responseEmployee = JsonTools.parse(response, Employee.class);
        Assert.assertEquals(null, responseEmployee);
    }

    @Test
    public void postEmployee() throws IOException {
        Employee employee = createEmployee();
        employee.name = "new name";

        String response = new Request(serverUrl + "/" + employee.id)
                .json(JsonTools.serialize(employee))
                .post()
                .asString();

        Employee responseEmployee = JsonTools.parse(response, Employee.class);

        Assert.assertEquals(employee.id, responseEmployee.id);
        Assert.assertEquals(employee.name, responseEmployee.name);
        Assert.assertEquals(employee.createdDate, responseEmployee.createdDate);
    }

    @Test
    public void getAllEmployees() throws IOException {
        createEmployee();
        createEmployee();

        String response = new Request(serverUrl + "/")
                .get()
                .asString();

        List<Employee> list = JsonTools.parse(response, List.class, Employee.class);

        Assert.assertTrue(list.size() >= 2);
    }
}
