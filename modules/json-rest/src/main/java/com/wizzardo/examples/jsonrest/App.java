package com.wizzardo.examples.jsonrest;

import com.wizzardo.examples.jsonrest.controller.EmployeeRestController;
import com.wizzardo.http.framework.WebApplication;

/**
 * Created by wizzardo on 29.03.16.
 */
public class App {
    final WebApplication server;

    public App() {
        server = new WebApplication();
        server.getUrlMapping()
                .append("/rest/employees/$id?", new EmployeeRestController())
                ;
        server.start();
    }

    public static void main(String[] args) {
        new App();
    }
}
