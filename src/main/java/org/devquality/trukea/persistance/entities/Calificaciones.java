package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class Calificaciones {

    private Long id;
    private Long usuarioCalificadorId;
    private Long usuarioCalificadoId;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fecha;

    public Calificaciones(Long id, Long usuarioCalificadorId, Long usuarioCalificadoId, Integer puntuacion, String comentario, LocalDateTime fecha) {
        this.id = id;
        this.usuarioCalificadorId = usuarioCalificadorId;
        this.usuarioCalificadoId = usuarioCalificadoId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Calificaciones() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioCalificadorId() {
        return usuarioCalificadorId;
    }

    public void setUsuarioCalificadorId(Long usuarioCalificadorId) {
        this.usuarioCalificadorId = usuarioCalificadorId;
    }

    public Long getUsuarioCalificadoId() {
        return usuarioCalificadoId;
    }

    public void setUsuarioCalificadoId(Long usuarioCalificadoId) {
        this.usuarioCalificadoId = usuarioCalificadoId;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}