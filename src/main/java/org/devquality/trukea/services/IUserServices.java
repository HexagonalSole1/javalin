package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.web.dtos.usuarios.request.CreateUsuarioRequest;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;

import java.util.ArrayList;

public interface IUserServices {

    // READ operations
    ArrayList<CreateUsuarioResponse> findAll();
    Usuario findByEmail(String email);
    Usuario findById(Long id);

    // CREATE operation
    CreateUsuarioResponse createUser(CreateUsuarioRequest request);

    // UPDATE operation
    CreateUsuarioResponse updateUser(Long id, CreateUsuarioRequest request);

    // DELETE operation
    boolean deleteUser(Long id);

    // UTILITY operations
    boolean existsByEmail(String email);
    boolean existsById(Long id);
}