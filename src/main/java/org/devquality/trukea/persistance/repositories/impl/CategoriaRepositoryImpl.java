package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.persistance.repositories.ICategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaRepositoryImpl implements ICategoriaRepository {

    private final DatabaseConfig databaseConfig;
    private static final String SELECT_ALL_CATEGORIAS = "SELECT id, nombre FROM categorias";
    private static final String SELECT_CATEGORIA_BY_ID = "SELECT id, nombre FROM categorias WHERE id = ?";
    private static final Logger logger = LoggerFactory.getLogger(CategoriaRepositoryImpl.class);

    public CategoriaRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Categoria> findAllCategorias() {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Categoria> categorias = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIAS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getLong("id"));
                categoria.setNombre(resultSet.getString("nombre"));
                categorias.add(categoria);
                logger.info("Categoria found: {}", categoria.getNombre());
            }
            return categorias;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Categoria findById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIA_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getLong("id"));
                categoria.setNombre(resultSet.getString("nombre"));
                return categoria;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}