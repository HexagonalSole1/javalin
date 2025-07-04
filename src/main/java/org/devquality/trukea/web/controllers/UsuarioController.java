package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.services.IUserServices;
import org.devquality.trukea.web.dtos.common.ErrorResponse;
import org.devquality.trukea.web.dtos.usuarios.request.CreateUsuarioRequest;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UsuarioController {
    private final IUserServices userService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(IUserServices userService) {
        this.userService = userService;
    }

    // GET /api/usuarios
    public void getAllUsuarios(Context ctx) {
        try {
            ArrayList<CreateUsuarioResponse> usuarios = userService.findAll();
            if (usuarios.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("No se encontraron usuarios"));
                return;
            }
            ctx.status(HttpStatus.OK).json(usuarios);
        } catch (Exception e) {
            logger.error("Error in getAllUsuarios", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/usuarios/{email}
    public void getUserByEmail(Context ctx) {
        try {
            String email = ctx.pathParam("email");
            if (email == null || email.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("El email es requerido"));
                return;
            }

            Usuario usuario = userService.findByEmail(email);
            if (usuario == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Usuario no encontrado con email: " + email));
                return;
            }

            ctx.status(HttpStatus.OK).json(usuario);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getUserByEmail", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // GET /api/usuarios/id/{id}
    public void getUserById(Context ctx) {
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

            Usuario usuario = userService.findById(id);
            if (usuario == null) {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Usuario no encontrado con ID: " + id));
                return;
            }

            ctx.status(HttpStatus.OK).json(usuario);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in getUserById", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // POST /api/usuarios
    public void createUser(Context ctx) {
        try {
            CreateUsuarioRequest request = ctx.bodyAsClass(CreateUsuarioRequest.class);

            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos de usuario inválidos. Nombre, correo y contraseña son requeridos"));
                return;
            }

            CreateUsuarioResponse usuarioCreado = userService.createUser(request);
            ctx.status(HttpStatus.CREATED).json(usuarioCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in createUser", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // PUT /api/usuarios/{id}
    public void updateUser(Context ctx) {
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

            CreateUsuarioRequest request = ctx.bodyAsClass(CreateUsuarioRequest.class);

            if (request == null || !request.isValid()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Datos de usuario inválidos. Nombre, correo y contraseña son requeridos"));
                return;
            }

            CreateUsuarioResponse usuarioActualizado = userService.updateUser(id, request);
            ctx.status(HttpStatus.OK).json(usuarioActualizado);

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in updateUser", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }

    // DELETE /api/usuarios/{id}
    public void deleteUser(Context ctx) {
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

            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                ctx.status(HttpStatus.NO_CONTENT).result("");
            } else {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Usuario no encontrado con ID: " + id));
            }

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in deleteUser", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(new ErrorResponse("Error interno del servidor: " + e.getMessage()));
        }
    }
}