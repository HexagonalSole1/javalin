package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Trueque;

import java.util.ArrayList;

public interface ITruequeRepository {

    // READ operations
    ArrayList<Trueque> findAllTrueques();
    Trueque findById(Long id);
    ArrayList<Trueque> findByEstado(String estado);
    ArrayList<Trueque> findByProductoOfrecidoId(Long productoId);
    ArrayList<Trueque> findByProductoDeseadoId(Long productoId);

    // CREATE operation
    Trueque createTrueque(Trueque trueque);

    // UPDATE operation
    Trueque updateTrueque(Trueque trueque);

    // DELETE operation
    boolean deleteTrueque(Long id);

    // UTILITY operations
    boolean existsById(Long id);
    int countByEstado(String estado);
    boolean existsByProductos(Long productoOfrecidoId, Long productoDeseadoId);
}