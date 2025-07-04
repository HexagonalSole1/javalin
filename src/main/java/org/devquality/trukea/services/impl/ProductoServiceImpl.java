package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.devquality.trukea.services.IProductoService;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;

import java.util.ArrayList;

public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository productoRepository;

    public ProductoServiceImpl(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public ArrayList<CreateProductoResponse> findAll() {
        ArrayList<Producto> productos = productoRepository.findAllProductos();
        ArrayList<CreateProductoResponse> productoResponses = new ArrayList<>();

        for (Producto producto : productos) {
            CreateProductoResponse createProductoResponse = new CreateProductoResponse(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCategoriaId(),
                    producto.getUsuarioId()
            );
            productoResponses.add(createProductoResponse);
        }
        return productoResponses;
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public ArrayList<CreateProductoResponse> findByUsuarioId(Long usuarioId) {
        ArrayList<Producto> productos = productoRepository.findByUsuarioId(usuarioId);
        ArrayList<CreateProductoResponse> productoResponses = new ArrayList<>();

        for (Producto producto : productos) {
            CreateProductoResponse createProductoResponse = new CreateProductoResponse(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCategoriaId(),
                    producto.getUsuarioId()
            );
            productoResponses.add(createProductoResponse);
        }
        return productoResponses;
    }

    @Override
    public ArrayList<CreateProductoResponse> findByCategoriaId(Long categoriaId) {
        ArrayList<Producto> productos = productoRepository.findByCategoriaId(categoriaId);
        ArrayList<CreateProductoResponse> productoResponses = new ArrayList<>();

        for (Producto producto : productos) {
            CreateProductoResponse createProductoResponse = new CreateProductoResponse(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCategoriaId(),
                    producto.getUsuarioId()
            );
            productoResponses.add(createProductoResponse);
        }
        return productoResponses;
    }
}