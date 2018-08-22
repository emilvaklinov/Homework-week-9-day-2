package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

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


        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/departments", (req, res) -> {
            String title = req.queryParams("title");

            Department addingDepartment = new Department(title);
            DBHelper.save(addingDepartment);

            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());

//        get("/departments/:id", (req, res) -> {
//            int departmentId = Integer.parseInt(req.queryParams("id"));
//            Department foundDepartment = DBHelper.find(departmentId, Department.class);
//            model.put("template", "templates/departments/index.vtl");
//            model.put("departments", departments);
//            return new ModelAndView(model, "templates/layout.vtl");
//        }, new VelocityTemplateEngine());


    }


}
