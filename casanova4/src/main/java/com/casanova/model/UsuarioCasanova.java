package com.casanova.model;

public class UsuarioCasanova {

    private int id_usuario;
    private String nombre;
    private String email;
    private String telefono;
    private String password_hash;

    public enum Tipo {
        anfitrion, huesped, admin
    }

    private Tipo tipo;

    // ==============================
    // CONSTRUCTORES
    // ==============================

    public UsuarioCasanova() {}

    public UsuarioCasanova(int id_usuario, String nombre, String email,
                           String telefono, String password_hash, Tipo tipo) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.password_hash = password_hash;
        this.tipo = tipo;
    }

    // ==============================
    // GETTERS Y SETTERS
    // ==============================

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPassword_hash() { return password_hash; }
    public void setPassword_hash(String password_hash) { this.password_hash = password_hash; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "UsuarioCasanova{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
