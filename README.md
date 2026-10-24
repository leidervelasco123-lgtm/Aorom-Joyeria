# Aorom Joyería — módulo de inventario

Aplicación de consola en Java para administrar productos de joyería. Implementa operaciones CRUD usando JDBC y SQLite.

## Alcance y supuestos

El espacio de trabajo no contenía diagramas ni entregables anteriores. Para iniciar la codificación se tomó como módulo el inventario. Cada producto tiene código, nombre, categoría, material, precio en COP, cantidad disponible, descripción y fecha de creación. La aplicación crea la tabla automáticamente y guarda la base en `data/aorom_joyeria.db`.

## Requisitos

- JDK 17 o superior.
- Apache Maven 3.9 o superior.
- Git.

## Ejecución

Desde la carpeta del proyecto:

```bash
mvn clean compile exec:java
```

La primera ejecución descarga el controlador JDBC de SQLite mediante Maven. Para cambiar la ubicación de la base, define la variable de entorno `AOROM_DB_PATH` antes de iniciar la aplicación.

## Funcionalidades

- Registrar un producto.
- Listar todos los productos y buscar uno por código.
- Actualizar los datos de un producto.
- Eliminar un producto.
- Validar campos obligatorios, precio y cantidad no negativos.

Las consultas usan `PreparedStatement` y el acceso a recursos JDBC usa `try-with-resources`.

## Estructura

```text
src/main/java/co/aorom/joyeria/
├── Main.java
├── config/DatabaseConnection.java
├── model/Producto.java
└── repository/ProductoRepository.java
database/schema.sql
```

## Versionamiento

El proyecto está preparado para Git. Si aún no es un repositorio, inicialízalo así:

```bash
git init
git add .
git commit -m "Crear módulo de inventario Aorom Joyería"
```

## Artefactos de referencia

Este entregable documenta el supuesto de inventario debido a que no se encontraron artefactos previos en el espacio de trabajo. Antes de presentar la versión final académica, contrasta los campos y operaciones con el diagrama de clases, casos de uso, historias de usuario y prototipos de Aorom Joyería.
