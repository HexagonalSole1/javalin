package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.persistance.repositories.ITruequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TruequeRepositoryImpl implements ITruequeRepository {

    private final DatabaseConfig databaseConfig;
    private static final String SELECT_ALL_TRUEQUES = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques";
    private static final String SELECT_TRUEQUE_BY_ID = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques WHERE id = ?";
    private static final String SELECT_TRUEQUES_BY_ESTADO = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques WHERE estado = ?";
    private static final Logger logger = LoggerFactory.getLogger(TruequeRepositoryImpl.class);

    public TruequeRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Trueque> findAllTrueques() {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Trueque> trueques = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TRUEQUES);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trueque trueque = mapResultSetToTrueque(resultSet);
                trueques.add(trueque);
                logger.info("Trueque found: {}", trueque.getId());
            }
            return trueques;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trueque findById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRUEQUE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToTrueque(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Trueque> findByEstado(String estado) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Trueque> trueques = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRUEQUES_BY_ESTADO);
            preparedStatement.setString(1, estado);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trueque trueque = mapResultSetToTrueque(resultSet);
                trueques.add(trueque);
            }
            return trueques;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Trueque mapResultSetToTrueque(ResultSet resultSet) throws SQLException {
        Trueque trueque = new Trueque();
        trueque.setId(resultSet.getLong("id"));
        trueque.setProductoOfrecidoId(resultSet.getLong("producto_ofrecido_id"));
        trueque.setProductoDeseadoId(resultSet.getLong("producto_deseado_id"));
        trueque.setEstado(resultSet.getString("estado"));
        trueque.setFecha(resultSet.getTimestamp("fecha").toLocalDateTime());
        return trueque;
    }
}