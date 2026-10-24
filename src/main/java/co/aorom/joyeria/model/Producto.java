package co.aorom.joyeria.model;

import java.math.BigDecimal;

public class Producto {
    private long id;
    private final String nombre;
    private final String categoria;
    private final String material;
    private final BigDecimal precio;
    private final int cantidad;
    private final String descripcion;

    public Producto(String nombre, String categoria, String material, BigDecimal precio, int cantidad, String descripcion) {
        if (nombre == null || nombre.isBlank() || categoria == null || categoria.isBlank() || material == null || material.isBlank())
            throw new IllegalArgumentException("Nombre, categoría y material son obligatorios.");
        if (precio == null || precio.signum() < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        if (cantidad < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        this.nombre = nombre.trim();
        this.categoria = categoria.trim();
        this.material = material.trim();
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion == null ? "" : descripcion.trim();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getMaterial() { return material; }
    public BigDecimal getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return "Producto{id=%d, nombre='%s', categoría='%s', material='%s', precio=%s COP, cantidad=%d, descripción='%s'}"
                .formatted(id, nombre, categoria, material, precio.toPlainString(), cantidad, descripcion);
    }
}
