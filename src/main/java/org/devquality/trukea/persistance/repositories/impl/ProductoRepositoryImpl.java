package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class ProductoRepositoryImpl implements IProductoRepository {

    private final DatabaseConfig databaseConfig;

    // SELECT queries
    private static final String SELECT_ALL_PRODUCTOS = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos";
    private static final String SELECT_PRODUCTO_BY_ID = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos WHERE id = ?";
    private static final String SELECT_PRODUCTOS_BY_USUARIO = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos WHERE usuario_id = ?";
    private static final String SELECT_PRODUCTOS_BY_CATEGORIA = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos WHERE categoria_id = ?";

    // INSERT queries
    private static final String INSERT_PRODUCTO = "INSERT INTO productos (nombre, descripcion, categoria_id, usuario_id) VALUES (?, ?, ?, ?)";

    // UPDATE queries
    private static final String UPDATE_PRODUCTO = "UPDATE productos SET nombre = ?, descripcion = ?, categoria_id = ?, usuario_id = ? WHERE id = ?";

    // DELETE queries
    private static final String DELETE_PRODUCTO = "DELETE FROM productos WHERE id = ?";

    // EXISTS and COUNT queries
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM productos WHERE id = ?";
    private static final String COUNT_BY_USUARIO = "SELECT COUNT(*) FROM productos WHERE usuario_id = ?";
    private static final String COUNT_BY_CATEGORIA = "SELECT COUNT(*) FROM productos WHERE categoria_id = ?";

    private static final Logger logger = LoggerFactory.getLogger(ProductoRepositoryImpl.class);

    public ProductoRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Producto> findAllProductos() {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Producto> productos = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTOS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Producto producto = mapResultSetToProducto(resultSet);
                productos.add(producto);
                logger.debug("Producto found: {}", producto.getNombre());
            }
            return productos;

        } catch (SQLException e) {
            logger.error("Error finding all productos", e);
            throw new RuntimeException("Error al obtener todos los productos", e);
        }
    }

    @Override
    public Producto findById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTO_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToProducto(resultSet);
            }

        } catch (SQLException e) {
            logger.error("Error finding producto by id: {}", id, e);
            throw new RuntimeException("Error al buscar producto por ID", e);
        }
        return null;
    }

    @Override
    public ArrayList<Producto> findByUsuarioId(Long usuarioId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Producto> productos = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTOS_BY_USUARIO);
            preparedStatement.setLong(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Producto producto = mapResultSetToProducto(resultSet);
                productos.add(producto);
            }
            return productos;

        } catch (SQLException e) {
            logger.error("Error finding productos by usuario id: {}", usuarioId, e);
            throw new RuntimeException("Error al buscar productos por usuario", e);
        }
    }

    @Override
    public ArrayList<Producto> findByCategoriaId(Long categoriaId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Producto> productos = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTOS_BY_CATEGORIA);
            preparedStatement.setLong(1, categoriaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Producto producto = mapResultSetToProducto(resultSet);
                productos.add(producto);
            }
            return productos;

        } catch (SQLException e) {
            logger.error("Error finding productos by categoria id: {}", categoriaId, e);
            throw new RuntimeException("Error al buscar productos por categoría", e);
        }
    }

    @Override
    public Producto createProducto(Producto producto) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setLong(3, producto.getCategoriaId());
            preparedStatement.setLong(4, producto.getUsuarioId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating producto failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getLong(1));
                    logger.info("Producto created successfully with ID: {}", producto.getId());
                    return producto;
                } else {
                    throw new SQLException("Creating producto failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("Error creating producto: {}", producto.getNombre(), e);
            throw new RuntimeException("Error al crear producto", e);
        }
    }

    @Override
    public Producto updateProducto(Producto producto) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTO);
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setLong(3, producto.getCategoriaId());
            preparedStatement.setLong(4, producto.getUsuarioId());
            preparedStatement.setLong(5, producto.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating producto failed, no rows affected.");
            }

            logger.info("Producto updated successfully with ID: {}", producto.getId());
            return producto;

        } catch (SQLException e) {
            logger.error("Error updating producto: {}", producto.getId(), e);
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public boolean deleteProducto(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTO);
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            boolean deleted = affectedRows > 0;

            if (deleted) {
                logger.info("Producto deleted successfully with ID: {}", id);
            } else {
                logger.warn("No producto found to delete with ID: {}", id);
            }

            return deleted;

        } catch (SQLException e) {
            logger.error("Error deleting producto: {}", id, e);
            throw new RuntimeException("Error al eliminar producto", e);
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
            logger.error("Error checking if producto exists by id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de producto", e);
        }
        return false;
    }

    @Override
    public int countByUsuarioId(Long usuarioId) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_USUARIO);
            preparedStatement.setLong(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.error("Error counting productos by usuario id: {}", usuarioId, e);
            throw new RuntimeException("Error al contar productos por usuario", e);
        }
        return 0;
    }

    @Override
    public int countByCategoriaId(Long categoriaId) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_CATEGORIA);
            preparedStatement.setLong(1, categoriaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.error("Error counting productos by categoria id: {}", categoriaId, e);
            throw new RuntimeException("Error al contar productos por categoría", e);
        }
        return 0;
    }

    private Producto mapResultSetToProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setDescripcion(resultSet.getString("descripcion"));
        producto.setCategoriaId(resultSet.getLong("categoria_id"));
        producto.setUsuarioId(resultSet.getLong("usuario_id"));
        return producto;
    }
}