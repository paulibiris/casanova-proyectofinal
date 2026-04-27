package com.casanova.dao;

import com.casanova.model.UsuarioCasanova;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {

    /**
     * Obtiene todos los usuarios registrados.
     */
    List<UsuarioCasanova> findAll();

    /**
     * Busca un usuario por su ID.
     */
    Optional<UsuarioCasanova> findById(int id_usuario);

    /**
     * Busca un usuario por su email.
     */
    Optional<UsuarioCasanova> findByEmail(String email);

    /**
     * Verifica credenciales para login.
     * Retorna el usuario si email + password coinciden, o vacío si no.
     */
    Optional<UsuarioCasanova> login(String email, String password, String tipo);

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Retorna el número de filas afectadas.
     */
    int insert(UsuarioCasanova usuario);

    /**
     * Actualiza los datos de un usuario existente.
     * Retorna el número de filas afectadas.
     */
    int update(UsuarioCasanova usuario);

    /**
     * Elimina un usuario por su ID.
     * Retorna el número de filas afectadas.
     */
    int delete(int id_usuario);

    /**
     * Cuenta el total de usuarios.
     */
    int count();
}
