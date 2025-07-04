package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.TruequeController;

public class TruequesRoutes {

    private final TruequeController controller;

    public TruequesRoutes(TruequeController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        app.get("/api/trueques", controller::getAllTrueques);
        app.get("/api/trueques/{id}", controller::getTruequeById);
        app.get("/api/trueques/estado/{estado}", controller::getTruequesByEstado);
    }
}