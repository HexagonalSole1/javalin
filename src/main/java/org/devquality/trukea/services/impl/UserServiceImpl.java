package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.persistance.repositories.IUsuarioRepository;
import org.devquality.trukea.services.IUserServices;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;

import java.util.ArrayList;

public class UserServiceImpl implements IUserServices {

    public UserServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private final IUsuarioRepository usuarioRepository;

    @Override
    public ArrayList<CreateUsuarioResponse> findAll() {

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepository.findAllUsers();

        ArrayList<CreateUsuarioResponse> usuarioResponses = new ArrayList<>();

        for (Usuario usuario: usuarios ) {
            CreateUsuarioResponse createUsuarioResponse = new CreateUsuarioResponse( usuario.getNombre()
                    ,usuario.getCorreo());

            usuarioResponses.add(createUsuarioResponse);
        }
        return usuarioResponses;
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
