package com.casanova.dao;

import com.casanova.model.ComentariosCasanova;
import java.util.List;
import java.util.Optional;

public interface ComentariosDAO {

    /**
     * Obtiene todos los comentarios.
     */
    List<ComentariosCasanova> findAll();

    /**
     * Busca un comentario por su ID.
     */
    Optional<ComentariosCasanova> findById(int id_comentario);

    /**
     * Obtiene todos los comentarios de una propiedad.
     */
    List<ComentariosCasanova> findByPropiedad(int id_propiedad);

    /**
     * Obtiene todos los comentarios de un usuario.
     */
    List<ComentariosCasanova> findByUsuario(int id_usuario);

    /**
     * Calcula la puntuación media de una propiedad.
     */
    Double getPuntuacionMedia(int id_propiedad);

    /**
     * Inserta un nuevo comentario.
     * Retorna el número de filas afectadas.
     */
    int insert(ComentariosCasanova comentario);

    /**
     * Actualiza un comentario existente.
     * Retorna el número de filas afectadas.
     */
    int update(ComentariosCasanova comentario);

    /**
     * Elimina un comentario por su ID.
     * Retorna el número de filas afectadas.
     */
    int delete(int id_comentario);

    /**
     * Cuenta el total de comentarios de una propiedad.
     */
    int countByPropiedad(int id_propiedad);
}
