package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.CalificacionesController;

public class CalificacionesRoutes {

    private final CalificacionesController controller;

    public CalificacionesRoutes(CalificacionesController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        app.get("/api/calificaciones", controller::getAllCalificaciones);
        app.get("/api/calificaciones/{id}", controller::getCalificacionById);
        app.get("/api/calificaciones/usuario/{usuarioId}", controller::getCalificacionesByUsuario);
    }
}