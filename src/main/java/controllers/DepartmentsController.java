package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class DepartmentsController {
    public DepartmentsController(){
        setupEndpoints();
    }

    private void setupEndpoints(){
        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department>departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}
