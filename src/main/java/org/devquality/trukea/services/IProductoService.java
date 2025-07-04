package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.web.dtos.productos.request.CreateProductoRequest;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;

import java.util.ArrayList;

public interface IProductoService {

    // READ operations
    ArrayList<CreateProductoResponse> findAll();
    Producto findById(Long id);
    ArrayList<CreateProductoResponse> findByUsuarioId(Long usuarioId);
    ArrayList<CreateProductoResponse> findByCategoriaId(Long categoriaId);

    // CREATE operation
    CreateProductoResponse createProducto(CreateProductoRequest request);

    // UPDATE operation
    CreateProductoResponse updateProducto(Long id, CreateProductoRequest request);

    // DELETE operation
    boolean deleteProducto(Long id);

    // UTILITY operations
    boolean existsById(Long id);
    int countByUsuarioId(Long usuarioId);
    int countByCategoriaId(Long categoriaId);
}