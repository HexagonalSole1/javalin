package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.UsuarioController;

public class UsuariosRoutes {

    private final UsuarioController controller;


    public UsuariosRoutes(UsuarioController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        app.get("/api/usuarios", controller::getAllUsuarios);
        app.get("/api/usuarios/{email}", controller::getUserByEmail);
    }
}
