package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class Trueque {

    private Long id;
    private Long productoOfrecidoId;
    private Long productoDeseadoId;
    private String estado;
    private LocalDateTime fecha;

    public Trueque(Long id, Long productoOfrecidoId, Long productoDeseadoId, String estado, LocalDateTime fecha) {
        this.id = id;
        this.productoOfrecidoId = productoOfrecidoId;
        this.productoDeseadoId = productoDeseadoId;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Trueque() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoOfrecidoId() {
        return productoOfrecidoId;
    }

    public void setProductoOfrecidoId(Long productoOfrecidoId) {
        this.productoOfrecidoId = productoOfrecidoId;
    }

    public Long getProductoDeseadoId() {
        return productoDeseadoId;
    }

    public void setProductoDeseadoId(Long productoDeseadoId) {
        this.productoDeseadoId = productoDeseadoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}