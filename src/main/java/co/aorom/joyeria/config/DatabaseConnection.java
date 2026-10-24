package co.aorom.joyeria.config;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private final String url;

    public DatabaseConnection() {
        String configuredPath = System.getenv().getOrDefault("AOROM_DB_PATH", "data/aorom_joyeria.db");
        Path databasePath = Path.of(configuredPath);
        if (databasePath.getParent() != null) {
            try { java.nio.file.Files.createDirectories(databasePath.getParent()); }
            catch (java.io.IOException exception) { throw new IllegalStateException("No se pudo crear la carpeta de datos.", exception); }
        }
        url = "jdbc:sqlite:" + databasePath;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void initialize() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS productos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL CHECK(length(trim(nombre)) > 0),
                    categoria TEXT NOT NULL CHECK(length(trim(categoria)) > 0),
                    material TEXT NOT NULL CHECK(length(trim(material)) > 0),
                    precio NUMERIC NOT NULL CHECK(precio >= 0),
                    cantidad INTEGER NOT NULL CHECK(cantidad >= 0),
                    descripcion TEXT NOT NULL DEFAULT '',
                    fecha_creacion TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
