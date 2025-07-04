package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;

import java.util.ArrayList;

public interface IProductoService {

    ArrayList<CreateProductoResponse> findAll();

    Producto findById(Long id);

    ArrayList<CreateProductoResponse> findByUsuarioId(Long usuarioId);

    ArrayList<CreateProductoResponse> findByCategoriaId(Long categoriaId);
}