package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.persistance.repositories.ICategoriaRepository;
import org.devquality.trukea.services.ICategoriaService;
import org.devquality.trukea.web.dtos.categorias.response.CreateCategoriaResponse;

import java.util.ArrayList;

public class CategoriaServiceImpl implements ICategoriaService {

    private final ICategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ArrayList<CreateCategoriaResponse> findAll() {
        ArrayList<Categoria> categorias = categoriaRepository.findAllCategorias();
        ArrayList<CreateCategoriaResponse> categoriaResponses = new ArrayList<>();

        for (Categoria categoria : categorias) {
            CreateCategoriaResponse createCategoriaResponse = new CreateCategoriaResponse(
                    categoria.getId(),
                    categoria.getNombre()
            );
            categoriaResponses.add(createCategoriaResponse);
        }
        return categoriaResponses;
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id);
    }
}