package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.web.controllers.ProductoController;

public class ProductosRoutes {

    private final ProductoController controller;

    public ProductosRoutes(ProductoController controller) {
        this.controller = controller;
    }

    public void configure(Javalin app) {
        app.get("/api/productos", controller::getAllProductos);
        app.get("/api/productos/{id}", controller::getProductoById);
        app.get("/api/productos/usuario/{usuarioId}", controller::getProductosByUsuario);
        app.get("/api/productos/categoria/{categoriaId}", controller::getProductosByCategoria);
    }
}