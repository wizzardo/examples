package com.wizzardo.examples.jsonrest;

import com.wizzardo.examples.jsonrest.model.Employee;
import com.wizzardo.tools.http.Request;
import com.wizzardo.tools.json.JsonTools;
import com.wizzardo.tools.misc.With;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by wizzardo on 31.03.16.
 */
public class TestApp {

    String serverUrl = "http://localhost:8080/rest/employees";

    @Test
    public void createEmployee() throws IOException {
        Employee employee = With.with(new Employee(), it -> {
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
    }

}
