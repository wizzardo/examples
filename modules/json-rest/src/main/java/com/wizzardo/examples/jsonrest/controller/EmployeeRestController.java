package com.wizzardo.examples.jsonrest.controller;

import com.wizzardo.examples.jsonrest.model.Employee;
import com.wizzardo.http.RestHandler;
import com.wizzardo.http.request.Header;
import com.wizzardo.http.response.Response;
import com.wizzardo.tools.json.JsonTools;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wizzardo on 30.03.16.
 */
public class EmployeeRestController extends RestHandler {
    private Map<Integer, Employee> data = new HashMap<>();

    public EmployeeRestController() {
        get = (request, response) -> {
            int id = request.params().getInt("id", -1);
            if (id != -1)
                return renderJson(data.get(id), response);
            else
                return renderJson(data.values(), response);
        };
    }

    private Response renderJson(Object data, Response response) {
        return response
                .setBody(JsonTools.serializeToBytes(data))
                .appendHeader(Header.KV_CONTENT_TYPE_APPLICATION_JSON)
                ;
    }
}
