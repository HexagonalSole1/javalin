package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.CategoriaController;

public class CategoriasRoutes {

    private final CategoriaController controller;

    public CategoriasRoutes(CategoriaController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        app.get("/api/categorias", controller::getAllCategorias);
        app.get("/api/categorias/{id}", controller::getCategoriaById);
    }
}