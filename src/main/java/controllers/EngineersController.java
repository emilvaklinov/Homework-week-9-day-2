package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;


public class EngineersController {

    public EngineersController() {
        setupEndpoints();
    }


    private void setupEndpoints(){

        get("/engineers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/index.vtl");

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        //        # new
//        get '/className/new'

        get("/engineers/new", (req, res) -> {

            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//     # create
//        post '/className'

        post("/engineers", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            Engineer engineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(engineer);
            res.redirect("/engineers");
            return null;
        }, new VelocityTemplateEngine());


        // # edit
//        get '/className/:id/edit'

        get("/engineers/:id/edit", (req, res) -> {

                    Map<String, Object> model = new HashMap<>();
                    model.put("template", "templates/engineers/edit.vtl");

                    int engineer = Integer.parseInt(req.params(":id"));
                    Engineer engineerId = DBHelper.find(engineer, Engineer.class);
                    model.put("engineer", engineer);

                    List<Department> departments = DBHelper.getAll(Department.class);
                    model.put("departments", departments);

                    return new ModelAndView(model, "templates/layout.vtl");

                }, new VelocityTemplateEngine()
        );


        //        # update
//        post '/className/:id'


        post("/engineers/:id", (req, res) -> {

                    int engineerId = Integer.parseInt(req.params(":id"));
                    Engineer engineer = DBHelper.find(engineerId, Engineer.class);

                    String firstName = req.queryParams("firstName");
                    String lastName = req.queryParams("lastName");
                    int salary = Integer.parseInt(req.queryParams("salary"));
                    int departmentId = Integer.parseInt(req.queryParams("departmentId"));
                    Department department = DBHelper.find(departmentId, Department.class);

                    //update with new values of properties
                    engineer.setFirstName(firstName);
                    engineer.setLastName(lastName);
                    engineer.setSalary(salary);
                    engineer.setDepartment(department);

                    //update in the db and redirect to the managers page
                    DBHelper.save(engineer);
                    res.redirect("/engineers");
                    return null;
                }
        );

        //        # destroy
//        get '/className/:id/delete'

        post("/engineers/:id/delete", (req, res) -> {
            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(engineerId, Engineer.class);
            DBHelper.delete(engineer);
            res.redirect("/engineers");
            return null;
        });
    }
    }

