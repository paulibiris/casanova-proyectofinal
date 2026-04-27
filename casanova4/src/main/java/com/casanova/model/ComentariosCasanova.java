package com.casanova.model;

import java.time.LocalDate;

public class ComentariosCasanova {

    private int id_comentario;
    private int id_propiedad;
    private int id_usuario;
    private String comentario;
    private int puntuacion;
    private LocalDate fecha;

    // ==============================
    // CONSTRUCTORES
    // ==============================

    public ComentariosCasanova() {}

    public ComentariosCasanova(int id_comentario, int id_propiedad, int id_usuario,
                               String comentario, int puntuacion, LocalDate fecha) {
        this.id_comentario = id_comentario;
        this.id_propiedad = id_propiedad;
        this.id_usuario = id_usuario;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
    }

    // ==============================
    // GETTERS Y SETTERS
    // ==============================

    public int getId_comentario() { return id_comentario; }
    public void setId_comentario(int id_comentario) { this.id_comentario = id_comentario; }

    public int getId_propiedad() { return id_propiedad; }
    public void setId_propiedad(int id_propiedad) { this.id_propiedad = id_propiedad; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "ComentariosCasanova{" +
                "id_comentario=" + id_comentario +
                ", id_propiedad=" + id_propiedad +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
