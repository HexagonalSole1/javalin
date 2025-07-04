package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Calificaciones;

import java.util.ArrayList;

public interface ICalificacionesRepository {

    ArrayList<Calificaciones> findAllCalificaciones();

    Calificaciones findById(Long id);

    ArrayList<Calificaciones> findByUsuarioCalificadoId(Long usuarioId);
}