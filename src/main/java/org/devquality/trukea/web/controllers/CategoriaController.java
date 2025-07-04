package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.services.ICategoriaService;
import org.devquality.trukea.web.dtos.categorias.response.CreateCategoriaResponse;

import java.util.ArrayList;

public class CategoriaController {

    private final ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public void getAllCategorias(Context ctx) {
        try {
            ArrayList<CreateCategoriaResponse> categorias = categoriaService.findAll();
            ctx.status(HttpStatus.OK).json(categorias);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getCategoriaById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Categoria categoria = categoriaService.findById(id);
            ctx.status(HttpStatus.OK).json(categoria);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }
}