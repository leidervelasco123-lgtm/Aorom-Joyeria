package co.aorom.joyeria;

import co.aorom.joyeria.config.DatabaseConnection;
import co.aorom.joyeria.model.Producto;
import co.aorom.joyeria.repository.ProductoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ProductoRepository productoRepository;

    private Main() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.initialize();
        productoRepository = new ProductoRepository(databaseConnection);
    }

    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (SQLException exception) {
            System.err.println("No fue posible iniciar la base de datos: " + exception.getMessage());
        }
    }

    private void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Aorom Joyería | Inventario ===");
            System.out.println("1. Registrar producto\n2. Listar productos\n3. Buscar producto\n4. Actualizar producto\n5. Eliminar producto\n0. Salir");
            int option = readInt("Seleccione una opción: ");
            try {
                switch (option) {
                    case 1 -> createProduct();
                    case 2 -> listProducts();
                    case 3 -> findProduct();
                    case 4 -> updateProduct();
                    case 5 -> deleteProduct();
                    case 0 -> running = false;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException exception) {
                System.err.println("Error al acceder a los datos: " + exception.getMessage());
            }
        }
        System.out.println("Sesión finalizada.");
    }

    private void createProduct() throws SQLException {
        Producto producto = readProductFields();
        long id = productoRepository.insertar(producto);
        System.out.println("Producto registrado con código " + id + ".");
    }

    private void listProducts() throws SQLException {
        List<Producto> products = productoRepository.consultarTodos();
        if (products.isEmpty()) System.out.println("No hay productos registrados.");
        else products.forEach(System.out::println);
    }

    private void findProduct() throws SQLException {
        long id = readLong("Código del producto: ");
        productoRepository.consultarPorId(id).ifPresentOrElse(System.out::println,
                () -> System.out.println("No se encontró un producto con ese código."));
    }

    private void updateProduct() throws SQLException {
        long id = readLong("Código del producto a actualizar: ");
        if (productoRepository.consultarPorId(id).isEmpty()) {
            System.out.println("No se encontró un producto con ese código.");
            return;
        }
        Producto product = readProductFields();
        product.setId(id);
        System.out.println(productoRepository.actualizar(product) ? "Producto actualizado." : "No se pudo actualizar el producto.");
    }

    private void deleteProduct() throws SQLException {
        long id = readLong("Código del producto a eliminar: ");
        System.out.println(productoRepository.eliminar(id) ? "Producto eliminado." : "No se encontró un producto con ese código.");
    }

    private Producto readProductFields() {
        String nombre = readText("Nombre: ");
        String categoria = readText("Categoría (anillo, collar, aretes, pulsera, otro): ");
        String material = readText("Material: ");
        BigDecimal precio = readDecimal("Precio en COP: ");
        int cantidad = readNonNegativeInt("Cantidad disponible: ");
        String descripcion = readText("Descripción: ");
        return new Producto(nombre, categoria, material, precio, cantidad, descripcion);
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Este campo es obligatorio.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            try { System.out.print(prompt); return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException exception) { System.out.println("Ingrese un número entero válido."); }
        }
    }

    private int readNonNegativeInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value >= 0) return value;
            System.out.println("El valor no puede ser negativo.");
        }
    }

    private long readLong(String prompt) {
        while (true) {
            try { System.out.print(prompt); return Long.parseLong(scanner.nextLine().trim()); }
            catch (NumberFormatException exception) { System.out.println("Ingrese un código numérico válido."); }
        }
    }

    private BigDecimal readDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                BigDecimal value = new BigDecimal(scanner.nextLine().trim().replace(',', '.'));
                if (value.signum() >= 0) return value;
                System.out.println("El precio no puede ser negativo.");
            }
            catch (NumberFormatException exception) { System.out.println("Ingrese un precio válido, por ejemplo 125000.00."); }
        }
    }
}
