package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.services.IProductoService;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;

import java.util.ArrayList;

public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    public void getAllProductos(Context ctx) {
        try {
            ArrayList<CreateProductoResponse> productos = productoService.findAll();
            ctx.status(HttpStatus.OK).json(productos);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getProductoById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Producto producto = productoService.findById(id);
            ctx.status(HttpStatus.OK).json(producto);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getProductosByUsuario(Context ctx) {
        try {
            Long usuarioId = Long.parseLong(ctx.pathParam("usuarioId"));
            ArrayList<CreateProductoResponse> productos = productoService.findByUsuarioId(usuarioId);
            ctx.status(HttpStatus.OK).json(productos);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getProductosByCategoria(Context ctx) {
        try {
            Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));
            ArrayList<CreateProductoResponse> productos = productoService.findByCategoriaId(categoriaId);
            ctx.status(HttpStatus.OK).json(productos);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }
}