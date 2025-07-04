package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.web.dtos.trueques.request.CreateTruequeRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;

import java.util.ArrayList;

public interface ITruequeService {

    // READ operations
    ArrayList<CreateTruequeResponse> findAll();
    Trueque findById(Long id);
    ArrayList<CreateTruequeResponse> findByEstado(String estado);
    ArrayList<CreateTruequeResponse> findByProductoOfrecidoId(Long productoId);
    ArrayList<CreateTruequeResponse> findByProductoDeseadoId(Long productoId);

    // CREATE operation
    CreateTruequeResponse createTrueque(CreateTruequeRequest request);

    // UPDATE operation
    CreateTruequeResponse updateTrueque(Long id, CreateTruequeRequest request);

    // DELETE operation
    boolean deleteTrueque(Long id);

    // UTILITY operations
    boolean existsById(Long id);
    int countByEstado(String estado);
    boolean existsByProductos(Long productoOfrecidoId, Long productoDeseadoId);
}