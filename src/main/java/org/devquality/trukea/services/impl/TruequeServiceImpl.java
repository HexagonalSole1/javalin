package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.persistance.repositories.ITruequeRepository;
import org.devquality.trukea.services.ITruequeService;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;

import java.util.ArrayList;

public class TruequeServiceImpl implements ITruequeService {

    private final ITruequeRepository truequeRepository;

    public TruequeServiceImpl(ITruequeRepository truequeRepository) {
        this.truequeRepository = truequeRepository;
    }

    @Override
    public ArrayList<CreateTruequeResponse> findAll() {
        ArrayList<Trueque> trueques = truequeRepository.findAllTrueques();
        ArrayList<CreateTruequeResponse> truequeResponses = new ArrayList<>();

        for (Trueque trueque : trueques) {
            CreateTruequeResponse createTruequeResponse = new CreateTruequeResponse(
                    trueque.getId(),
                    trueque.getProductoOfrecidoId(),
                    trueque.getProductoDeseadoId(),
                    trueque.getEstado(),
                    trueque.getFecha()
            );
            truequeResponses.add(createTruequeResponse);
        }
        return truequeResponses;
    }

    @Override
    public Trueque findById(Long id) {
        return truequeRepository.findById(id);
    }

    @Override
    public ArrayList<CreateTruequeResponse> findByEstado(String estado) {
        ArrayList<Trueque> trueques = truequeRepository.findByEstado(estado);
        ArrayList<CreateTruequeResponse> truequeResponses = new ArrayList<>();

        for (Trueque trueque : trueques) {
            CreateTruequeResponse createTruequeResponse = new CreateTruequeResponse(
                    trueque.getId(),
                    trueque.getProductoOfrecidoId(),
                    trueque.getProductoDeseadoId(),
                    trueque.getEstado(),
                    trueque.getFecha()
            );
            truequeResponses.add(createTruequeResponse);
        }
        return truequeResponses;
    }
}