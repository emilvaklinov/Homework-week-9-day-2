package controllers;

import db.DBHelper;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;


public class ManagersController {
    public ManagersController(){
        setupEndpoints();

    }

    private void setupEndpoints(){
        get("/managers", (req, res) -> {

            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
//            TODO send managers over
            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);

            return new ModelAndView( model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

    }
}
