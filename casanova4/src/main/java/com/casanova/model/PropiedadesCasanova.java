package com.casanova.model;

public class PropiedadesCasanova {

    private int id_propiedad;
    private int id_usuario;
    private String titulo;
    private String descripcion;
    private String direccion;
    private String ciudad;
    private String pais;
    private double precio_noche;
    private int capacidad;
    private String tipo_operacion; // compra / alquiler
    private String tipo_propiedad; // Casa, Apartamento, Chalet, Atico
    private boolean activa;
    private String foto; // nombre del archivo, ej: "casa_madrid.jpg"

    // ==============================
    // CONSTRUCTORES
    // ==============================

    public PropiedadesCasanova() {}

    public PropiedadesCasanova(int id_propiedad, int id_usuario, String titulo,
                               String descripcion, String direccion, String ciudad,
                               String pais, double precio_noche, int capacidad,
                               String tipo_operacion, String tipo_propiedad, boolean activa) {
        this.id_propiedad = id_propiedad;
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.precio_noche = precio_noche;
        this.capacidad = capacidad;
        this.tipo_operacion = tipo_operacion;
        this.tipo_propiedad = tipo_propiedad;
        this.activa = activa;
    }

    // ==============================
    // GETTERS Y SETTERS
    // ==============================

    public int getId_propiedad() { return id_propiedad; }
    public void setId_propiedad(int id_propiedad) { this.id_propiedad = id_propiedad; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public double getPrecio_noche() { return precio_noche; }
    public void setPrecio_noche(double precio_noche) { this.precio_noche = precio_noche; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getTipo_operacion() { return tipo_operacion; }
    public void setTipo_operacion(String tipo_operacion) { this.tipo_operacion = tipo_operacion; }

    public String getTipo_propiedad() { return tipo_propiedad; }
    public void setTipo_propiedad(String tipo_propiedad) { this.tipo_propiedad = tipo_propiedad; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    @Override
    public String toString() {
        return "PropiedadesCasanova{" +
                "id_propiedad=" + id_propiedad +
                ", titulo='" + titulo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", precio_noche=" + precio_noche +
                '}';
    }
}
