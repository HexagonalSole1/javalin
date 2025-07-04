package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Calificaciones;
import org.devquality.trukea.persistance.repositories.ICalificacionesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CalificacionesRepositoryImpl implements ICalificacionesRepository {

    private final DatabaseConfig databaseConfig;
    private static final String SELECT_ALL_CALIFICACIONES = "SELECT id, usuario_calificador_id, usuario_calificado_id, puntuacion, comentario, fecha FROM calificaciones";
    private static final String SELECT_CALIFICACION_BY_ID = "SELECT id, usuario_calificador_id, usuario_calificado_id, puntuacion, comentario, fecha FROM calificaciones WHERE id = ?";
    private static final String SELECT_CALIFICACIONES_BY_USUARIO = "SELECT id, usuario_calificador_id, usuario_calificado_id, puntuacion, comentario, fecha FROM calificaciones WHERE usuario_calificado_id = ?";
    private static final Logger logger = LoggerFactory.getLogger(CalificacionesRepositoryImpl.class);

    public CalificacionesRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Calificaciones> findAllCalificaciones() {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Calificaciones> calificaciones = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CALIFICACIONES);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Calificaciones calificacion = mapResultSetToCalificacion(resultSet);
                calificaciones.add(calificacion);
                logger.info("Calificacion found: {}", calificacion.getId());
            }
            return calificaciones;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Calificaciones findById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CALIFICACION_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToCalificacion(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Calificaciones> findByUsuarioCalificadoId(Long usuarioId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Calificaciones> calificaciones = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CALIFICACIONES_BY_USUARIO);
            preparedStatement.setLong(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Calificaciones calificacion = mapResultSetToCalificacion(resultSet);
                calificaciones.add(calificacion);
            }
            return calificaciones;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Calificaciones mapResultSetToCalificacion(ResultSet resultSet) throws SQLException {
        Calificaciones calificacion = new Calificaciones();
        calificacion.setId(resultSet.getLong("id"));
        calificacion.setUsuarioCalificadorId(resultSet.getLong("usuario_calificador_id"));
        calificacion.setUsuarioCalificadoId(resultSet.getLong("usuario_calificado_id"));
        calificacion.setPuntuacion(resultSet.getInt("puntuacion"));
        calificacion.setComentario(resultSet.getString("comentario"));
        calificacion.setFecha(resultSet.getTimestamp("fecha").toLocalDateTime());
        return calificacion;
    }
}