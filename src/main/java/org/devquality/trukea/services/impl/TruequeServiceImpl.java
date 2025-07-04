package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.persistance.repositories.ITruequeRepository;
import org.devquality.trukea.services.ITruequeService;
import org.devquality.trukea.web.dtos.trueques.request.CreateTruequeRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TruequeServiceImpl implements ITruequeService {

    private final ITruequeRepository truequeRepository;
    private static final Logger logger = LoggerFactory.getLogger(TruequeServiceImpl.class);

    public TruequeServiceImpl(ITruequeRepository truequeRepository) {
        this.truequeRepository = truequeRepository;
    }

    @Override
    public ArrayList<CreateTruequeResponse> findAll() {
        try {
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
        } catch (Exception e) {
            logger.error("Error in findAll trueques service", e);
            throw new RuntimeException("Error al obtener todos los trueques", e);
        }
    }

    @Override
    public Trueque findById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }
            return truequeRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error in findById service for id: {}", id, e);
            throw new RuntimeException("Error al buscar trueque por ID", e);
        }
    }

    @Override
    public ArrayList<CreateTruequeResponse> findByEstado(String estado) {
        try {
            if (estado == null || estado.trim().isEmpty()) {
                throw new IllegalArgumentException("El estado es requerido");
            }

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
        } catch (Exception e) {
            logger.error("Error in findByEstado service for estado: {}", estado, e);
            throw new RuntimeException("Error al buscar trueques por estado", e);
        }
    }

    @Override
    public ArrayList<CreateTruequeResponse> findByProductoOfrecidoId(Long productoId) {
        try {
            if (productoId == null || productoId <= 0) {
                throw new IllegalArgumentException("El ID del producto debe ser un número positivo");
            }

            ArrayList<Trueque> trueques = truequeRepository.findByProductoOfrecidoId(productoId);
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
        } catch (Exception e) {
            logger.error("Error in findByProductoOfrecidoId service for productoId: {}", productoId, e);
            throw new RuntimeException("Error al buscar trueques por producto ofrecido", e);
        }
    }

    @Override
    public ArrayList<CreateTruequeResponse> findByProductoDeseadoId(Long productoId) {
        try {
            if (productoId == null || productoId <= 0) {
                throw new IllegalArgumentException("El ID del producto debe ser un número positivo");
            }

            ArrayList<Trueque> trueques = truequeRepository.findByProductoDeseadoId(productoId);
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
        } catch (Exception e) {
            logger.error("Error in findByProductoDeseadoId service for productoId: {}", productoId, e);
            throw new RuntimeException("Error al buscar trueques por producto deseado", e);
        }
    }

    @Override
    public CreateTruequeResponse createTrueque(CreateTruequeRequest request) {
        try {
            // Validar request
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Los datos del trueque son inválidos");
            }

            // Validar que no sea el mismo producto
            if (request.getProductoOfrecidoId().equals(request.getProductoDeseadoId())) {
                throw new IllegalArgumentException("No se puede intercambiar un producto por sí mismo");
            }

            // Verificar que no exista ya un trueque activo con los mismos productos
            if (truequeRepository.existsByProductos(request.getProductoOfrecidoId(), request.getProductoDeseadoId())) {
                throw new IllegalArgumentException("Ya existe un trueque activo entre estos productos");
            }

            // Crear entidad Trueque
            Trueque trueque = new Trueque();
            trueque.setProductoOfrecidoId(request.getProductoOfrecidoId());
            trueque.setProductoDeseadoId(request.getProductoDeseadoId());
            trueque.setEstado(request.getEstado() != null ? request.getEstado() : "pendiente");
            trueque.setFecha(LocalDateTime.now());

            // Guardar en base de datos
            Trueque truequeCreado = truequeRepository.createTrueque(trueque);

            // Retornar DTO de respuesta
            return new CreateTruequeResponse(
                    truequeCreado.getId(),
                    truequeCreado.getProductoOfrecidoId(),
                    truequeCreado.getProductoDeseadoId(),
                    truequeCreado.getEstado(),
                    truequeCreado.getFecha()
            );

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in createTrueque: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in createTrueque service", e);
            throw new RuntimeException("Error al crear trueque", e);
        }
    }

    @Override
    public CreateTruequeResponse updateTrueque(Long id, CreateTruequeRequest request) {
        try {
            // Validar parámetros
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Los datos del trueque son inválidos");
            }

            // Verificar que el trueque existe
            Trueque truequeExistente = truequeRepository.findById(id);
            if (truequeExistente == null) {
                throw new IllegalArgumentException("Trueque no encontrado con ID: " + id);
            }

            // Validar que no sea el mismo producto
            if (request.getProductoOfrecidoId().equals(request.getProductoDeseadoId())) {
                throw new IllegalArgumentException("No se puede intercambiar un producto por sí mismo");
            }

            // Si los productos cambiaron, verificar que no exista otro trueque activo
            if (!truequeExistente.getProductoOfrecidoId().equals(request.getProductoOfrecidoId()) ||
                    !truequeExistente.getProductoDeseadoId().equals(request.getProductoDeseadoId())) {

                if (truequeRepository.existsByProductos(request.getProductoOfrecidoId(), request.getProductoDeseadoId())) {
                    throw new IllegalArgumentException("Ya existe un trueque activo entre estos productos");
                }
            }

            // Actualizar datos
            truequeExistente.setProductoOfrecidoId(request.getProductoOfrecidoId());
            truequeExistente.setProductoDeseadoId(request.getProductoDeseadoId());
            truequeExistente.setEstado(request.getEstado());
            // Mantener la fecha original, no actualizarla

            // Guardar cambios
            Trueque truequeActualizado = truequeRepository.updateTrueque(truequeExistente);

            // Retornar DTO de respuesta
            return new CreateTruequeResponse(
                    truequeActualizado.getId(),
                    truequeActualizado.getProductoOfrecidoId(),
                    truequeActualizado.getProductoDeseadoId(),
                    truequeActualizado.getEstado(),
                    truequeActualizado.getFecha()
            );

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in updateTrueque: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in updateTrueque service for id: {}", id, e);
            throw new RuntimeException("Error al actualizar trueque", e);
        }
    }

    @Override
    public boolean deleteTrueque(Long id) {
        try {
            // Validar parámetro
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }

            // Verificar que el trueque existe
            if (!truequeRepository.existsById(id)) {
                throw new IllegalArgumentException("Trueque no encontrado con ID: " + id);
            }

            // Eliminar trueque
            return truequeRepository.deleteTrueque(id);

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in deleteTrueque: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in deleteTrueque service for id: {}", id, e);
            throw new RuntimeException("Error al eliminar trueque", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return truequeRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Error in existsById service for id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de trueque", e);
        }
    }

    @Override
    public int countByEstado(String estado) {
        try {
            if (estado == null || estado.trim().isEmpty()) {
                return 0;
            }
            return truequeRepository.countByEstado(estado);
        } catch (Exception e) {
            logger.error("Error in countByEstado service for estado: {}", estado, e);
            throw new RuntimeException("Error al contar trueques por estado", e);
        }
    }

    @Override
    public boolean existsByProductos(Long productoOfrecidoId, Long productoDeseadoId) {
        try {
            if (productoOfrecidoId == null || productoOfrecidoId <= 0 ||
                    productoDeseadoId == null || productoDeseadoId <= 0) {
                return false;
            }
            return truequeRepository.existsByProductos(productoOfrecidoId, productoDeseadoId);
        } catch (Exception e) {
            logger.error("Error in existsByProductos service for productos: {} -> {}", productoOfrecidoId, productoDeseadoId, e);
            throw new RuntimeException("Error al verificar existencia de trueque por productos", e);
        }
    }
}