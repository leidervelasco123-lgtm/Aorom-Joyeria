CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL CHECK(length(trim(nombre)) > 0),
    categoria TEXT NOT NULL CHECK(length(trim(categoria)) > 0),
    material TEXT NOT NULL CHECK(length(trim(material)) > 0),
    precio NUMERIC NOT NULL CHECK(precio >= 0),
    cantidad INTEGER NOT NULL CHECK(cantidad >= 0),
    descripcion TEXT NOT NULL DEFAULT '',
    fecha_creacion TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);
