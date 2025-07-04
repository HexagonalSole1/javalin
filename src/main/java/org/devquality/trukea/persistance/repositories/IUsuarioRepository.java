package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Usuario;

import java.util.ArrayList;

public interface IUsuarioRepository {

    // READ operations
    Usuario findByEmail(String email);
    Usuario findById(Long id);
    ArrayList<Usuario> findAllUsers();

    // CREATE operation
    Usuario createUser(Usuario usuario);

    // UPDATE operation
    Usuario updateUser(Usuario usuario);

    // DELETE operation
    boolean deleteUser(Long id);

    // UTILITY operations
    boolean existsByEmail(String email);
    boolean existsById(Long id);
}