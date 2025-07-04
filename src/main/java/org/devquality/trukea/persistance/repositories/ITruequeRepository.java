package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Trueque;

import java.util.ArrayList;

public interface ITruequeRepository {

    ArrayList<Trueque> findAllTrueques();

    Trueque findById(Long id);

    ArrayList<Trueque> findByEstado(String estado);
}