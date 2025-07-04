package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Calificaciones;
import org.devquality.trukea.web.dtos.calificaciones.response.CreateCalificacionesResponse;

import java.util.ArrayList;

public interface ICalificacionesService {

    ArrayList<CreateCalificacionesResponse> findAll();

    Calificaciones findById(Long id);

    ArrayList<CreateCalificacionesResponse> findByUsuarioCalificadoId(Long usuarioId);
}