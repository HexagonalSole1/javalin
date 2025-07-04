package org.devquality.trukea.web.dtos.productos.request;

public class CreateProductoRequest {

    private String nombre;
    private String descripcion;
    private Long categoriaId;
    private Long usuarioId;

    public CreateProductoRequest() {
    }

    public CreateProductoRequest(String nombre, String descripcion, Long categoriaId, Long usuarioId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoriaId = categoriaId;
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Método de validación
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                categoriaId != null && categoriaId > 0 &&
                usuarioId != null && usuarioId > 0;
    }
}