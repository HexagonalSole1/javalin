package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.UsuarioController;

public class UsuariosRoutes {

    private final UsuarioController controller;

    public UsuariosRoutes(UsuarioController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        // GET endpoints
        app.get("/api/usuarios", controller::getAllUsuarios);
        app.get("/api/usuarios/{email}", controller::getUserByEmail);
        app.get("/api/usuarios/id/{id}", controller::getUserById);

        // POST endpoint
        app.post("/api/usuarios", controller::createUser);

        // PUT endpoint
        app.put("/api/usuarios/{id}", controller::updateUser);

        // DELETE endpoint
        app.delete("/api/usuarios/{id}", controller::deleteUser);
    }
}