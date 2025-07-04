package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.persistance.repositories.IUsuarioRepository;
import org.devquality.trukea.services.IUserServices;
import org.devquality.trukea.web.dtos.usuarios.request.CreateUsuarioRequest;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UserServiceImpl implements IUserServices {

    private final IUsuarioRepository usuarioRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ArrayList<CreateUsuarioResponse> findAll() {
        try {
            ArrayList<Usuario> usuarios = usuarioRepository.findAllUsers();
            ArrayList<CreateUsuarioResponse> usuarioResponses = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                CreateUsuarioResponse createUsuarioResponse = new CreateUsuarioResponse(
                        usuario.getNombre(),
                        usuario.getCorreo()
                );
                usuarioResponses.add(createUsuarioResponse);
            }
            return usuarioResponses;
        } catch (Exception e) {
            logger.error("Error in findAll users service", e);
            throw new RuntimeException("Error al obtener todos los usuarios", e);
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("El email no puede estar vacío");
            }
            return usuarioRepository.findByEmail(email);
        } catch (Exception e) {
            logger.error("Error in findByEmail service for email: {}", email, e);
            throw new RuntimeException("Error al buscar usuario por email", e);
        }
    }

    @Override
    public Usuario findById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }
            return usuarioRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error in findById service for id: {}", id, e);
            throw new RuntimeException("Error al buscar usuario por ID", e);
        }
    }

    @Override
    public CreateUsuarioResponse createUser(CreateUsuarioRequest request) {
        try {
            // Validar request
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Los datos del usuario son inválidos");
            }

            // Verificar que el email no exista
            if (usuarioRepository.existsByEmail(request.getCorreo())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email");
            }

            // Crear entidad Usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setCorreo(request.getCorreo());
            usuario.setContrasenia(request.getContrasenia());

            // Guardar en base de datos
            Usuario usuarioCreado = usuarioRepository.createUser(usuario);

            // Retornar DTO de respuesta
            return new CreateUsuarioResponse(
                    usuarioCreado.getNombre(),
                    usuarioCreado.getCorreo()
            );

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in createUser: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in createUser service", e);
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    @Override
    public CreateUsuarioResponse updateUser(Long id, CreateUsuarioRequest request) {
        try {
            // Validar parámetros
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Los datos del usuario son inválidos");
            }

            // Verificar que el usuario existe
            Usuario usuarioExistente = usuarioRepository.findById(id);
            if (usuarioExistente == null) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
            }

            // Verificar que el email no esté en uso por otro usuario
            Usuario usuarioConEmail = usuarioRepository.findByEmail(request.getCorreo());
            if (usuarioConEmail != null && !usuarioConEmail.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro usuario con ese email");
            }

            // Actualizar datos
            usuarioExistente.setNombre(request.getNombre());
            usuarioExistente.setCorreo(request.getCorreo());
            usuarioExistente.setContrasenia(request.getContrasenia());

            // Guardar cambios
            Usuario usuarioActualizado = usuarioRepository.updateUser(usuarioExistente);

            // Retornar DTO de respuesta
            return new CreateUsuarioResponse(
                    usuarioActualizado.getNombre(),
                    usuarioActualizado.getCorreo()
            );

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in updateUser: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in updateUser service for id: {}", id, e);
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            // Validar parámetro
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }

            // Verificar que el usuario existe
            if (!usuarioRepository.existsById(id)) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
            }

            // Eliminar usuario
            return usuarioRepository.deleteUser(id);

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in deleteUser: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error in deleteUser service for id: {}", id, e);
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return false;
            }
            return usuarioRepository.existsByEmail(email);
        } catch (Exception e) {
            logger.error("Error in existsByEmail service for email: {}", email, e);
            throw new RuntimeException("Error al verificar existencia de usuario por email", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return usuarioRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Error in existsById service for id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de usuario por ID", e);
        }
    }
}