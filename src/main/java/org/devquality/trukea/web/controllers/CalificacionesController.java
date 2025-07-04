package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Calificaciones;
import org.devquality.trukea.services.ICalificacionesService;
import org.devquality.trukea.web.dtos.calificaciones.response.CreateCalificacionesResponse;

import java.util.ArrayList;

public class CalificacionesController {

    private final ICalificacionesService calificacionesService;

    public CalificacionesController(ICalificacionesService calificacionesService) {
        this.calificacionesService = calificacionesService;
    }

    public void getAllCalificaciones(Context ctx) {
        try {
            ArrayList<CreateCalificacionesResponse> calificaciones = calificacionesService.findAll();
            ctx.status(HttpStatus.OK).json(calificaciones);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getCalificacionById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Calificaciones calificacion = calificacionesService.findById(id);
            ctx.status(HttpStatus.OK).json(calificacion);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }

    public void getCalificacionesByUsuario(Context ctx) {
        try {
            Long usuarioId = Long.parseLong(ctx.pathParam("usuarioId"));
            ArrayList<CreateCalificacionesResponse> calificaciones = calificacionesService.findByUsuarioCalificadoId(usuarioId);
            ctx.status(HttpStatus.OK).json(calificaciones);
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }
    }
}