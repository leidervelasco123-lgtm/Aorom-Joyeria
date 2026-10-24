package co.aorom.joyeria.repository;

import co.aorom.joyeria.config.DatabaseConnection;
import co.aorom.joyeria.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoRepository {
    private final DatabaseConnection databaseConnection;

    public ProductoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public long insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, categoria, material, precio, cantidad, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            bindProduct(statement, producto);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
        }
        throw new SQLException("No se obtuvo el código del producto creado.");
    }

    public List<Producto> consultarTodos() throws SQLException {
        String sql = "SELECT id, nombre, categoria, material, precio, cantidad, descripcion FROM productos ORDER BY nombre";
        List<Producto> products = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) products.add(mapProduct(resultSet));
        }
        return products;
    }

    public Optional<Producto> consultarPorId(long id) throws SQLException {
        String sql = "SELECT id, nombre, categoria, material, precio, cantidad, descripcion FROM productos WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapProduct(resultSet)) : Optional.empty();
            }
        }
    }

    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, material = ?, precio = ?, cantidad = ?, descripcion = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bindProduct(statement, producto);
            statement.setLong(7, producto.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private void bindProduct(PreparedStatement statement, Producto producto) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getCategoria());
        statement.setString(3, producto.getMaterial());
        statement.setBigDecimal(4, producto.getPrecio());
        statement.setInt(5, producto.getCantidad());
        statement.setString(6, producto.getDescripcion());
    }

    private Producto mapProduct(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto(resultSet.getString("nombre"), resultSet.getString("categoria"),
                resultSet.getString("material"), resultSet.getBigDecimal("precio"), resultSet.getInt("cantidad"),
                resultSet.getString("descripcion"));
        producto.setId(resultSet.getLong("id"));
        return producto;
    }
}
