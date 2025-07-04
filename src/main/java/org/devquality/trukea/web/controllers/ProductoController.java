package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.services.IProductoService;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.productos.request.CreateProductoRequest;
import org.devquality.trukea.web.dtos.productos.response.CreateProductoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ProductoController {

    private final IProductoService productoService;
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /api/productos
    public void getAllProductos(Context ctx) {
        try {
            ArrayList<CreateProductoResponse> productos = productoService.findAll();
            if (productos.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron productos"));
                return;
            }
            ctx.status(HttpStatus.OK).json(productos);
        } catch (Exception e) {
            logger.error("Error in getAllProductos", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/productos/{id}
    public void getProductoById(Context ctx) {
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

            Producto producto = productoService.findById(id);
            if (producto == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Producto no encontrado con ID: " + id));
                return;
            }

            ctx.status(HttpStatus.OK).json(producto);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getProductoById", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/productos/usuario/{usuarioId}
    public void getProductosByUsuario(Context ctx) {
        try {
            String usuarioIdParam = ctx.pathParam("usuarioId");
            if (usuarioIdParam == null || usuarioIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del usuario es requerido"));
                return;
            }

            Long usuarioId;
            try {
                usuarioId = Long.parseLong(usuarioIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del usuario debe ser un número válido"));
                return;
            }

            ArrayList<CreateProductoResponse> productos = productoService.findByUsuarioId(usuarioId);
            if (productos.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron productos para el usuario con ID: " + usuarioId));
                return;
            }

            ctx.status(HttpStatus.OK).json(productos);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getProductosByUsuario", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/productos/categoria/{categoriaId}
    public void getProductosByCategoria(Context ctx) {
        try {
            String categoriaIdParam = ctx.pathParam("categoriaId");
            if (categoriaIdParam == null || categoriaIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID de la categoría es requerido"));
                return;
            }

            Long categoriaId;
            try {
                categoriaId = Long.parseLong(categoriaIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID de la categoría debe ser un número válido"));
                return;
            }

            ArrayList<CreateProductoResponse> productos = productoService.findByCategoriaId(categoriaId);
            if (productos.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron productos para la categoría con ID: " + categoriaId));
                return;
            }

            ctx.status(HttpStatus.OK).json(productos);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getProductosByCategoria", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // POST /api/productos
    public void createProducto(Context ctx) {
        try {
            CreateProductoRequest request = ctx.bodyAsClass(CreateProductoRequest.class);

            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos de producto inválidos. Nombre, descripción, categoría y usuario son requeridos"));
                return;
            }

            CreateProductoResponse productoCreado = productoService.createProducto(request);
            ctx.status(HttpStatus.CREATED).json(productoCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in createProducto", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // PUT /api/productos/{id}
    public void updateProducto(Context ctx) {
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

            CreateProductoRequest request = ctx.bodyAsClass(CreateProductoRequest.class);

            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos de producto inválidos. Nombre, descripción, categoría y usuario son requeridos"));
                return;
            }

            CreateProductoResponse productoActualizado = productoService.updateProducto(id, request);
            ctx.status(HttpStatus.OK).json(productoActualizado);

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in updateProducto", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // DELETE /api/productos/{id}
    public void deleteProducto(Context ctx) {
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

            boolean deleted = productoService.deleteProducto(id);
            if (deleted) {
                ctx.status(HttpStatus.NO_CONTENT).result("");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Producto no encontrado con ID: " + id));
            }

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in deleteProducto", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/productos/usuario/{usuarioId}/count
    public void countProductosByUsuario(Context ctx) {
        try {
            String usuarioIdParam = ctx.pathParam("usuarioId");
            if (usuarioIdParam == null || usuarioIdParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del usuario es requerido"));
                return;
            }

            Long usuarioId;
            try {
                usuarioId = Long.parseLong(usuarioIdParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El ID del usuario debe ser un número válido"));
                return;
            }

            int count = productoService.countByUsuarioId(usuarioId);
            ctx.status(HttpStatus.OK).json(new CountResponse(count));

        } catch (Exception e) {
            logger.error("Error in countProductosByUsuario", e);
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