package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;

import java.util.ArrayList;

public interface IUserServices {

    ArrayList<CreateUsuarioResponse> findAll();

    Usuario findByEmail(String email);
}
