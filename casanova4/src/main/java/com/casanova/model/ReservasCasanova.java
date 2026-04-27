package com.casanova.model;

import java.time.LocalDate;

public class ReservasCasanova {

    private int id_reserva;
    private int id_propiedad;
    private int id_usuario;
    private String fecha_inicio;
    private String fecha_fin;
    private Double precio_total;

    public enum Estado {
        pendiente, confirmada, cancelada
    }

    private Estado estado;
    private LocalDate fecha_reserva;

    // ==============================
    // CONSTRUCTORES
    // ==============================

    public ReservasCasanova() {}

    public ReservasCasanova(int id_reserva, int id_propiedad, int id_usuario,
                            String fecha_inicio, String fecha_fin, Double precio_total,
                            Estado estado, LocalDate fecha_reserva) {
        this.id_reserva = id_reserva;
        this.id_propiedad = id_propiedad;
        this.id_usuario = id_usuario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.precio_total = precio_total;
        this.estado = estado;
        this.fecha_reserva = fecha_reserva;
    }

    // ==============================
    // GETTERS Y SETTERS
    // ==============================

    public int getId_reserva() { return id_reserva; }
    public void setId_reserva(int id_reserva) { this.id_reserva = id_reserva; }

    public int getId_propiedad() { return id_propiedad; }
    public void setId_propiedad(int id_propiedad) { this.id_propiedad = id_propiedad; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(String fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public String getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(String fecha_fin) { this.fecha_fin = fecha_fin; }

    public Double getPrecio_total() { return precio_total; }
    public void setPrecio_total(Double precio_total) { this.precio_total = precio_total; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public LocalDate getFecha_reserva() { return fecha_reserva; }
    public void setFecha_reserva(LocalDate fecha_reserva) { this.fecha_reserva = fecha_reserva; }

    @Override
    public String toString() {
        return "ReservasCasanova{" +
                "id_reserva=" + id_reserva +
                ", id_propiedad=" + id_propiedad +
                ", estado=" + estado +
                ", precio_total=" + precio_total +
                '}';
    }
}
