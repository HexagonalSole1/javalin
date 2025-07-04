package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.services.ITruequeService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.trueques.request.CreateTruequeRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TruequeController {

    private final ITruequeService truequeService;
    private static final Logger logger = LoggerFactory.getLogger(TruequeController.class);

    public TruequeController(ITruequeService truequeService) {
        this.truequeService = truequeService;
    }

    // GET /api/trueques
    public void getAllTrueques(Context ctx) {
        try {
            ArrayList<CreateTruequeResponse> trueques = truequeService.findAll();
            if (trueques.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron trueques"));
                return;
            }
            ctx.status(HttpStatus.OK).json(trueques);
        } catch (Exception e) {
            logger.error("Error in getAllTrueques", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/trueques/{id}
    public void getTruequeById(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            Trueque trueque = truequeService.findById(id);
            if (trueque == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Trueque no encontrado con ID: " + id));
                return;
            }

            ctx.status(HttpStatus.OK).json(trueque);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getTruequeById", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/trueques/estado/{estado}
    public void getTruequesByEstado(Context ctx) {
        try {
            String estado = ctx.pathParam("estado");
            if (estado == null || estado.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El estado es requerido"));
                return;
            }

            ArrayList<CreateTruequeResponse> trueques = truequeService.findByEstado(estado);
            if (trueques.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron trueques con estado: " + estado));
                return;
            }

            ctx.status(HttpStatus.OK).json(trueques);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getTruequesByEstado", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/trueques/producto-ofrecido/{productoId}
    public void getTruequesByProductoOfrecido(Context ctx) {
        try {
            String productoIdParam = ctx.pathParam("productoId");
            if (productoIdParam == null || productoIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del producto es requerido"));
                return;
            }

            Long productoId;
            try {
                productoId = Long.parseLong(productoIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del producto debe ser un número válido"));
                return;
            }

            ArrayList<CreateTruequeResponse> trueques = truequeService.findByProductoOfrecidoId(productoId);
            if (trueques.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron trueques para el producto ofrecido con ID: " + productoId));
                return;
            }

            ctx.status(HttpStatus.OK).json(trueques);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getTruequesByProductoOfrecido", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/trueques/producto-deseado/{productoId}
    public void getTruequesByProductoDeseado(Context ctx) {
        try {
            String productoIdParam = ctx.pathParam("productoId");
            if (productoIdParam == null || productoIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del producto es requerido"));
                return;
            }

            Long productoId;
            try {
                productoId = Long.parseLong(productoIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del producto debe ser un número válido"));
                return;
            }

            ArrayList<CreateTruequeResponse> trueques = truequeService.findByProductoDeseadoId(productoId);
            if (trueques.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron trueques para el producto deseado con ID: " + productoId));
                return;
            }

            ctx.status(HttpStatus.OK).json(trueques);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getTruequesByProductoDeseado", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // POST /api/trueques
    public void createTrueque(Context ctx) {
        try {
            CreateTruequeRequest request = ctx.bodyAsClass(CreateTruequeRequest.class);

            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos de trueque inválidos. Producto ofrecido, producto deseado y estado válido son requeridos"));
                return;
            }

            CreateTruequeResponse truequeCreado = truequeService.createTrueque(request);
            ctx.status(HttpStatus.CREATED).json(truequeCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in createTrueque", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // PUT /api/trueques/{id}
    public void updateTrueque(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            CreateTruequeRequest request = ctx.bodyAsClass(CreateTruequeRequest.class);

            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos de trueque inválidos. Producto ofrecido, producto deseado y estado válido son requeridos"));
                return;
            }

            CreateTruequeResponse truequeActualizado = truequeService.updateTrueque(id, request);
            ctx.status(HttpStatus.OK).json(truequeActualizado);

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in updateTrueque", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // DELETE /api/trueques/{id}
    public void deleteTrueque(Context ctx) {
        try {
            String idParam = ctx.pathParam("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID es requerido"));
                return;
            }

            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            boolean deleted = truequeService.deleteTrueque(id);
            if (deleted) {
                ctx.status(HttpStatus.NO_CONTENT).result("");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Trueque no encontrado con ID: " + id));
            }

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in deleteTrueque", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/trueques/estado/{estado}/count
    public void countTruequesByEstado(Context ctx) {
        try {
            String estado = ctx.pathParam("estado");
            if (estado == null || estado.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El estado es requerido"));
                return;
            }

            int count = truequeService.countByEstado(estado);
            ctx.status(HttpStatus.OK).json(new CountResponse(count));

        } catch (Exception e) {
            logger.error("Error in countTruequesByEstado", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // Clase interna para respuestas de conteo
    public static class CountResponse {
        private int count;

        public CountResponse(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}