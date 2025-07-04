package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.ProductoController;


public class ProductosRoutes {

    private final ProductoController controller;

    public ProductosRoutes(ProductoController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        // GET endpoints
        app.get("/api/productos", controller::getAllProductos);
        app.get("/api/productos/{id}", controller::getProductoById);
        app.get("/api/productos/usuario/{usuarioId}", controller::getProductosByUsuario);
        app.get("/api/productos/categoria/{categoriaId}", controller::getProductosByCategoria);
        app.get("/api/productos/usuario/{usuarioId}/count", controller::countProductosByUsuario);

        // POST endpoint
        app.post("/api/productos", controller::createProducto);

        // PUT endpoint
        app.put("/api/productos/{id}", controller::updateProducto);

        // DELETE endpoint
        app.delete("/api/productos/{id}", controller::deleteProducto);
    }
}