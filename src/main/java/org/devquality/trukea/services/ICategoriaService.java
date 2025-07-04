package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.web.dtos.categorias.response.CreateCategoriaResponse;

import java.util.ArrayList;

public interface ICategoriaService {

    ArrayList<CreateCategoriaResponse> findAll();

    Categoria findById(Long id);
}