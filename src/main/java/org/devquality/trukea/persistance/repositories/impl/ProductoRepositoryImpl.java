package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Producto;
import org.devquality.trukea.persistance.repositories.IProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoRepositoryImpl implements IProductoRepository {

    private final DatabaseConfig databaseConfig;
    private static final String SELECT_ALL_PRODUCTOS = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos";
    private static final String SELECT_PRODUCTO_BY_ID = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos WHERE id = ?";
    private static final String SELECT_PRODUCTOS_BY_USUARIO = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos WHERE usuario_id = ?";
    private static final String SELECT_PRODUCTOS_BY_CATEGORIA = "SELECT id, nombre, descripcion, categoria_id, usuario_id FROM productos WHERE categoria_id = ?";
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
                logger.info("Producto found: {}", producto.getNombre());
            }
            return productos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
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