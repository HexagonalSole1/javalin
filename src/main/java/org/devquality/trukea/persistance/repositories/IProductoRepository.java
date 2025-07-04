package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Producto;

import java.util.ArrayList;

public interface IProductoRepository {

    ArrayList<Producto> findAllProductos();

    Producto findById(Long id);

    ArrayList<Producto> findByUsuarioId(Long usuarioId);

    ArrayList<Producto> findByCategoriaId(Long categoriaId);
}