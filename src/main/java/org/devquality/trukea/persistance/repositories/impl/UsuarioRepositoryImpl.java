package org.devquality.trukea.persistance.repositories.impl;

import com.sun.tools.javac.Main;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.persistance.repositories.IUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioRepositoryImpl implements IUsuarioRepository {

    private final DatabaseConfig databaseConfig;

    private static final String SELECT_USER =  "SELECT id,nombre,correo,contraseña from usuarios ";
    private static final String SELECT_USER_BY_EMAIL = "select id,nombre,correo,contraseña from usuarios where correo = ?";
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public UsuarioRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Usuario findByEmail(String email) {

        try(Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setContrasenia(resultSet.getString("contraseña"));
                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    @Override
    public ArrayList<Usuario> findAllUsers() {
        try(Connection connection = databaseConfig.getConnection()){

            ArrayList<Usuario> usuarios = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setContrasenia(resultSet.getString("contraseña"));

                usuarios.add(usuario);
                logger.info("Usuario found: {}", usuario);

            }
            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
