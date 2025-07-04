package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.devquality.trukea.services.IProductoService;
import org.devquality.trukea.web.dtos.productos.request.CreateProductoRequest;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository productoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    public ProductoServiceImpl(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public ArrayList<CreateProductoResponse> findAll() {
        try {
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
        } catch (Exception e) {
            logger.error("Error in findAll productos service", e);
            throw new RuntimeException("Error al obtener todos los productos", e);
        }
    }

    @Override
    public Producto findById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }
            return productoRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error in findById service for id: {}", id, e);
            throw new RuntimeException("Error al buscar producto por ID", e);
        }
    }

    @Override
    public ArrayList<CreateProductoResponse> findByUsuarioId(Long usuarioId) {
        try {
            if (usuarioId == null || usuarioId <= 0) {
                throw new IllegalArgumentException("El ID del usuario debe ser un número positivo");
            }

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
        } catch (Exception e) {
            logger.error("Error in findByUsuarioId service for usuarioId: {}", usuarioId, e);
            throw new RuntimeException("Error al buscar productos por usuario", e);
        }
    }

    @Override
    public ArrayList<CreateProductoResponse> findByCategoriaId(Long categoriaId) {
        try {
            if (categoriaId == null || categoriaId <= 0) {
                throw new IllegalArgumentException("El ID de la categoría debe ser un número positivo");
            }

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
        } catch (Exception e) {
            logger.error("Error in findByCategoriaId service for categoriaId: {}", categoriaId, e);
            throw new RuntimeException("Error al buscar productos por categoría", e);
        }
    }

    @Override
    public CreateProductoResponse createProducto(CreateProductoRequest request) {
        try {
            // Validar request
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Los datos del producto son inválidos");
            }

            // Crear entidad Producto
            Producto producto = new Producto();
            producto.setNombre(request.getNombre());
            producto.setDescripcion(request.getDescripcion());
            producto.setCategoriaId(request.getCategoriaId());
            producto.setUsuarioId(request.getUsuarioId());

            // Guardar en base de datos
            Producto productoCreado = productoRepository.createProducto(producto);

            // Retornar DTO de respuesta
            return new CreateProductoResponse(
                    productoCreado.getId(),
                    productoCreado.getNombre(),
                    productoCreado.getDescripcion(),
                    productoCreado.getCategoriaId(),
                    productoCreado.getUsuarioId()
            );

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in createProducto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in createProducto service", e);
            throw new RuntimeException("Error al crear producto", e);
        }
    }

    @Override
    public CreateProductoResponse updateProducto(Long id, CreateProductoRequest request) {
        try {
            // Validar parámetros
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Los datos del producto son inválidos");
            }

            // Verificar que el producto existe
            Producto productoExistente = productoRepository.findById(id);
            if (productoExistente == null) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
            }

            // Actualizar datos
            productoExistente.setNombre(request.getNombre());
            productoExistente.setDescripcion(request.getDescripcion());
            productoExistente.setCategoriaId(request.getCategoriaId());
            productoExistente.setUsuarioId(request.getUsuarioId());

            // Guardar cambios
            Producto productoActualizado = productoRepository.updateProducto(productoExistente);

            // Retornar DTO de respuesta
            return new CreateProductoResponse(
                    productoActualizado.getId(),
                    productoActualizado.getNombre(),
                    productoActualizado.getDescripcion(),
                    productoActualizado.getCategoriaId(),
                    productoActualizado.getUsuarioId()
            );

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in updateProducto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in updateProducto service for id: {}", id, e);
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public boolean deleteProducto(Long id) {
        try {
            // Validar parámetro
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }

            // Verificar que el producto existe
            if (!productoRepository.existsById(id)) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
            }

            // Eliminar producto
            return productoRepository.deleteProducto(id);

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in deleteProducto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in deleteProducto service for id: {}", id, e);
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return productoRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Error in existsById service for id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de producto", e);
        }
    }

    @Override
    public int countByUsuarioId(Long usuarioId) {
        try {
            if (usuarioId == null || usuarioId <= 0) {
                return 0;
            }
            return productoRepository.countByUsuarioId(usuarioId);
        } catch (Exception e) {
            logger.error("Error in countByUsuarioId service for usuarioId: {}", usuarioId, e);
            throw new RuntimeException("Error al contar productos por usuario", e);
        }
    }

    @Override
    public int countByCategoriaId(Long categoriaId) {
        try {
            if (categoriaId == null || categoriaId <= 0) {
                return 0;
            }
            return productoRepository.countByCategoriaId(categoriaId);
        } catch (Exception e) {
            logger.error("Error in countByCategoriaId service for categoriaId: {}", categoriaId, e);
            throw new RuntimeException("Error al contar productos por categoría", e);
        }
    }
}