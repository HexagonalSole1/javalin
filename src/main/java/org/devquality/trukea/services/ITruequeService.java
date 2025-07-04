package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;

import java.util.ArrayList;

public interface ITruequeService {

    ArrayList<CreateTruequeResponse> findAll();

    Trueque findById(Long id);

    ArrayList<CreateTruequeResponse> findByEstado(String estado);
}
