package com.wizzardo.examples.jsonrest.controller;

import com.wizzardo.examples.jsonrest.model.Employee;
import com.wizzardo.http.Handler;
import com.wizzardo.http.RestHandler;
import com.wizzardo.http.request.Header;
import com.wizzardo.http.request.Request;
import com.wizzardo.http.response.Response;
import com.wizzardo.tools.json.JsonTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wizzardo on 30.03.16.
 */
public class EmployeeRestController extends RestHandler {
    private Map<Integer, Employee> data = new HashMap<>();

    public EmployeeRestController() {
        get = (JsonResponseHandler) (request, response) -> {
            int id = request.params().getInt("id", -1);
            if (id != -1)
                return data.get(id);
            else
                return data.values();
        };
        put = (JsonResponseHandler) (request, response) -> {
            Employee employee = JsonTools.parse(request.getBody().bytes(), Employee.class);
            employee.id = data.size();
            data.put(employee.id, employee);
            return employee;
        };
    }

    private interface JsonResponseHandler extends Handler {

        Object process(Request request, Response response);

        default Response handle(Request request, Response response) throws IOException {
            return response
                    .setBody(JsonTools.serializeToBytes(process(request, response)))
                    .appendHeader(Header.KV_CONTENT_TYPE_APPLICATION_JSON);
        }
    }
}
