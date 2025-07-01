package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Usuario;

import java.util.ArrayList;

public interface IUsuarioRepository {

    Usuario findByEmail(String email);

    ArrayList<Usuario> findAllUsers();
}
