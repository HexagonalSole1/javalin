package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.services.ITruequeService;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;

import java.util.ArrayList;

public class TruequeController {

    private final ITruequeService truequeService;

    public TruequeController(ITruequeService truequeService) {
        this.truequeService = truequeService;
    }

    public void getAllTrueques(Context ctx) {
        try {
            ArrayList<CreateTruequeResponse> trueques = truequeService.findAll();
            ctx.status(HttpStatus.OK).json(trueques);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getTruequeById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Trueque trueque = truequeService.findById(id);
            ctx.status(HttpStatus.OK).json(trueque);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getTruequesByEstado(Context ctx) {
        try {
            String estado = ctx.pathParam("estado");
            ArrayList<CreateTruequeResponse> trueques = truequeService.findByEstado(estado);
            ctx.status(HttpStatus.OK).json(trueques);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }
}