package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.persistance.repositories.ITruequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TruequeRepositoryImpl implements ITruequeRepository {

    private final DatabaseConfig databaseConfig;

    // SELECT queries
    private static final String SELECT_ALL_TRUEQUES = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques";
    private static final String SELECT_TRUEQUE_BY_ID = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques WHERE id = ?";
    private static final String SELECT_TRUEQUES_BY_ESTADO = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques WHERE estado = ?";
    private static final String SELECT_TRUEQUES_BY_PRODUCTO_OFRECIDO = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques WHERE producto_ofrecido_id = ?";
    private static final String SELECT_TRUEQUES_BY_PRODUCTO_DESEADO = "SELECT id, producto_ofrecido_id, producto_deseado_id, estado, fecha FROM trueques WHERE producto_deseado_id = ?";

    // INSERT queries
    private static final String INSERT_TRUEQUE = "INSERT INTO trueques (producto_ofrecido_id, producto_deseado_id, estado, fecha) VALUES (?, ?, ?, ?)";

    // UPDATE queries
    private static final String UPDATE_TRUEQUE = "UPDATE trueques SET producto_ofrecido_id = ?, producto_deseado_id = ?, estado = ?, fecha = ? WHERE id = ?";

    // DELETE queries
    private static final String DELETE_TRUEQUE = "DELETE FROM trueques WHERE id = ?";

    // EXISTS and COUNT queries
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM trueques WHERE id = ?";
    private static final String COUNT_BY_ESTADO = "SELECT COUNT(*) FROM trueques WHERE estado = ?";
    private static final String EXISTS_BY_PRODUCTOS = "SELECT COUNT(*) FROM trueques WHERE producto_ofrecido_id = ? AND producto_deseado_id = ? AND estado != 'cancelado' AND estado != 'rechazado'";

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
                logger.debug("Trueque found: {}", trueque.getId());
            }
            return trueques;

        } catch (SQLException e) {
            logger.error("Error finding all trueques", e);
            throw new RuntimeException("Error al obtener todos los trueques", e);
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
            logger.error("Error finding trueque by id: {}", id, e);
            throw new RuntimeException("Error al buscar trueque por ID", e);
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
            logger.error("Error finding trueques by estado: {}", estado, e);
            throw new RuntimeException("Error al buscar trueques por estado", e);
        }
    }

    @Override
    public ArrayList<Trueque> findByProductoOfrecidoId(Long productoId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Trueque> trueques = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRUEQUES_BY_PRODUCTO_OFRECIDO);
            preparedStatement.setLong(1, productoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trueque trueque = mapResultSetToTrueque(resultSet);
                trueques.add(trueque);
            }
            return trueques;

        } catch (SQLException e) {
            logger.error("Error finding trueques by producto ofrecido id: {}", productoId, e);
            throw new RuntimeException("Error al buscar trueques por producto ofrecido", e);
        }
    }

    @Override
    public ArrayList<Trueque> findByProductoDeseadoId(Long productoId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Trueque> trueques = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRUEQUES_BY_PRODUCTO_DESEADO);
            preparedStatement.setLong(1, productoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trueque trueque = mapResultSetToTrueque(resultSet);
                trueques.add(trueque);
            }
            return trueques;

        } catch (SQLException e) {
            logger.error("Error finding trueques by producto deseado id: {}", productoId, e);
            throw new RuntimeException("Error al buscar trueques por producto deseado", e);
        }
    }

    @Override
    public Trueque createTrueque(Trueque trueque) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRUEQUE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, trueque.getProductoOfrecidoId());
            preparedStatement.setLong(2, trueque.getProductoDeseadoId());
            preparedStatement.setString(3, trueque.getEstado());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(trueque.getFecha()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating trueque failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trueque.setId(generatedKeys.getLong(1));
                    logger.info("Trueque created successfully with ID: {}", trueque.getId());
                    return trueque;
                } else {
                    throw new SQLException("Creating trueque failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("Error creating trueque", e);
            throw new RuntimeException("Error al crear trueque", e);
        }
    }

    @Override
    public Trueque updateTrueque(Trueque trueque) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRUEQUE);
            preparedStatement.setLong(1, trueque.getProductoOfrecidoId());
            preparedStatement.setLong(2, trueque.getProductoDeseadoId());
            preparedStatement.setString(3, trueque.getEstado());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(trueque.getFecha()));
            preparedStatement.setLong(5, trueque.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating trueque failed, no rows affected.");
            }

            logger.info("Trueque updated successfully with ID: {}", trueque.getId());
            return trueque;

        } catch (SQLException e) {
            logger.error("Error updating trueque: {}", trueque.getId(), e);
            throw new RuntimeException("Error al actualizar trueque", e);
        }
    }

    @Override
    public boolean deleteTrueque(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRUEQUE);
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            boolean deleted = affectedRows > 0;

            if (deleted) {
                logger.info("Trueque deleted successfully with ID: {}", id);
            } else {
                logger.warn("No trueque found to delete with ID: {}", id);
            }

            return deleted;

        } catch (SQLException e) {
            logger.error("Error deleting trueque: {}", id, e);
            throw new RuntimeException("Error al eliminar trueque", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.error("Error checking if trueque exists by id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de trueque", e);
        }
        return false;
    }

    @Override
    public int countByEstado(String estado) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_ESTADO);
            preparedStatement.setString(1, estado);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.error("Error counting trueques by estado: {}", estado, e);
            throw new RuntimeException("Error al contar trueques por estado", e);
        }
        return 0;
    }

    @Override
    public boolean existsByProductos(Long productoOfrecidoId, Long productoDeseadoId) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_PRODUCTOS);
            preparedStatement.setLong(1, productoOfrecidoId);
            preparedStatement.setLong(2, productoDeseadoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.error("Error checking if trueque exists by productos: {} -> {}", productoOfrecidoId, productoDeseadoId, e);
            throw new RuntimeException("Error al verificar existencia de trueque por productos", e);
        }
        return false;
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