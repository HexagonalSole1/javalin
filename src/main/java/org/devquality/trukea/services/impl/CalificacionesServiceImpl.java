package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Calificaciones;
import org.devquality.trukea.persistance.repositories.ICalificacionesRepository;
import org.devquality.trukea.services.ICalificacionesService;
import org.devquality.trukea.web.dtos.calificaciones.response.CreateCalificacionesResponse;

import java.util.ArrayList;

public class CalificacionesServiceImpl implements ICalificacionesService {

    private final ICalificacionesRepository calificacionesRepository;

    public CalificacionesServiceImpl(ICalificacionesRepository calificacionesRepository) {
        this.calificacionesRepository = calificacionesRepository;
    }

    @Override
    public ArrayList<CreateCalificacionesResponse> findAll() {
        ArrayList<Calificaciones> calificaciones = calificacionesRepository.findAllCalificaciones();
        ArrayList<CreateCalificacionesResponse> calificacionesResponses = new ArrayList<>();

        for (Calificaciones calificacion : calificaciones) {
            CreateCalificacionesResponse createCalificacionesResponse = new CreateCalificacionesResponse(
                    calificacion.getId(),
                    calificacion.getUsuarioCalificadorId(),
                    calificacion.getUsuarioCalificadoId(),
                    calificacion.getPuntuacion(),
                    calificacion.getComentario(),
                    calificacion.getFecha()
            );
            calificacionesResponses.add(createCalificacionesResponse);
        }
        return calificacionesResponses;
    }

    @Override
    public Calificaciones findById(Long id) {
        return calificacionesRepository.findById(id);
    }

    @Override
    public ArrayList<CreateCalificacionesResponse> findByUsuarioCalificadoId(Long usuarioId) {
        ArrayList<Calificaciones> calificaciones = calificacionesRepository.findByUsuarioCalificadoId(usuarioId);
        ArrayList<CreateCalificacionesResponse> calificacionesResponses = new ArrayList<>();

        for (Calificaciones calificacion : calificaciones) {
            CreateCalificacionesResponse createCalificacionesResponse = new CreateCalificacionesResponse(
                    calificacion.getId(),
                    calificacion.getUsuarioCalificadorId(),
                    calificacion.getUsuarioCalificadoId(),
                    calificacion.getPuntuacion(),
                    calificacion.getComentario(),
                    calificacion.getFecha()
            );
            calificacionesResponses.add(createCalificacionesResponse);
        }
        return calificacionesResponses;
    }
}