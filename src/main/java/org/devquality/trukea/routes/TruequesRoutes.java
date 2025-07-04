package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.TruequeController;

public class TruequesRoutes {

    private final TruequeController controller;

    public TruequesRoutes(TruequeController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        // GET endpoints
        app.get("/api/trueques", controller::getAllTrueques);
        app.get("/api/trueques/{id}", controller::getTruequeById);
        app.get("/api/trueques/estado/{estado}", controller::getTruequesByEstado);
        app.get("/api/trueques/producto-ofrecido/{productoId}", controller::getTruequesByProductoOfrecido);
        app.get("/api/trueques/producto-deseado/{productoId}", controller::getTruequesByProductoDeseado);
        app.get("/api/trueques/estado/{estado}/count", controller::countTruequesByEstado);

        // POST endpoint
        app.post("/api/trueques", controller::createTrueque);

        // PUT endpoint
        app.put("/api/trueques/{id}", controller::updateTrueque);

        // DELETE endpoint
        app.delete("/api/trueques/{id}", controller::deleteTrueque);
    }
}