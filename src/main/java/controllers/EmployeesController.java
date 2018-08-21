package controllers;

import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class EmployeesController {

    public EmployeesController() {
    setupEndpoints();
    }

    private void setupEndpoints() {
        Seeds.seedData();

        // Build GET route for /employees
        get("/employees", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Employee> employees = DBHelper.getAll(Employee.class);

            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");


        }, new VelocityTemplateEngine());

        //        # new
//        get '/className/new'

        get("/employees/new", (req, res) -> {

            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//     # create
//        post '/className'

        post("/employees", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            Employee employee = new Employee(firstName, lastName, salary, department) {
            };
            DBHelper.save(employee);
            res.redirect("/employees");
            return null;
        }, new VelocityTemplateEngine());


        // # edit
//        get '/className/:id/edit'

        get("/employees/:id/edit", (req, res) -> {

                    Map<String, Object> model = new HashMap<>();
                    model.put("template", "templates/employees/edit.vtl");

                    int employee = Integer.parseInt(req.params(":id"));
                    Employee employeeId = DBHelper.find(employee, Employee.class);
                    model.put("employee", employee);

                    List<Department> departments = DBHelper.getAll(Department.class);
                    model.put("departments", departments);

                    return new ModelAndView(model, "templates/layout.vtl");

                }, new VelocityTemplateEngine()
        );


        //        # update
//        post '/className/:id'


        post("/employees/:id", (req, res) -> {

                    int employeeId = Integer.parseInt(req.params(":id"));
                    Employee employee = DBHelper.find(employeeId, Employee.class);

                    String firstName = req.queryParams("firstName");
                    String lastName = req.queryParams("lastName");
                    int salary = Integer.parseInt(req.queryParams("salary"));
                    int departmentId = Integer.parseInt(req.queryParams("departmentId"));
                    Department department = DBHelper.find(departmentId, Department.class);

                    //update with new values of properties
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);
                    employee.setSalary(salary);
                    employee.setDepartment(department);

                    //update in the db and redirect to the managers page
                    DBHelper.save(employee);
                    res.redirect("/employees");
                    return null;
                }
        );

        //        # destroy
//        get '/className/:id/delete'

        post("/employees/:id/delete", (req, res) -> {
            int employeeId = Integer.parseInt(req.params(":id"));
            Employee employee = DBHelper.find(employeeId, Employee.class);
            DBHelper.delete(employee);
            res.redirect("/employees");
            return null;
        });
    }
}
