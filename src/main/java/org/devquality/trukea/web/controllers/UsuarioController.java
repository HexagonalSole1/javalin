package org.devquality.trukea.web.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.services.IUserServices;
import org.devquality.trukea.web.dtos.usuarios.response.CreateUsuarioResponse;

import java.util.ArrayList;

public class UsuarioController {
    private final IUserServices userService;

    public UsuarioController(IUserServices userService) {
        this.userService = userService;
    }


    public void getAllUsuarios(Context ctx) {

        try{
            ArrayList<CreateUsuarioResponse> usuarios = userService.findAll();
            ctx.status(HttpStatus.OK).json(usuarios);
        }catch (Exception e){
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }

    }

    public void getUserByEmail(Context ctx) {

        try{
            Usuario usuario = userService.findByEmail(ctx.pathParam("email"));
            ctx.status(HttpStatus.OK).json(usuario);
        }catch (Exception e){
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(e.getMessage());
        }

    }
}
