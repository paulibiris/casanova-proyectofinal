package com.casanova.dao;

import com.casanova.model.ReservasCasanova;
import java.util.List;
import java.util.Optional;

public interface ReservasDAO {

    /**
     * Obtiene todas las reservas.
     */
    List<ReservasCasanova> findAll();

    /**
     * Busca una reserva por su ID.
     */
    Optional<ReservasCasanova> findById(int id_reserva);

    /**
     * Obtiene todas las reservas de un usuario (huésped).
     */
    List<ReservasCasanova> findByUsuario(int id_usuario);

    /**
     * Obtiene todas las reservas de una propiedad.
     */
    List<ReservasCasanova> findByPropiedad(int id_propiedad);

    /**
     * Obtiene reservas filtradas por estado.
     */
    List<ReservasCasanova> findByEstado(ReservasCasanova.Estado estado);

    /**
     * Inserta una nueva reserva.
     * Retorna el número de filas afectadas.
     */
    int insert(ReservasCasanova reserva);

    /**
     * Actualiza el estado de una reserva.
     * Retorna el número de filas afectadas.
     */
    int updateEstado(int id_reserva, ReservasCasanova.Estado nuevoEstado);

    /**
     * Actualiza los datos completos de una reserva.
     * Retorna el número de filas afectadas.
     */
    int update(ReservasCasanova reserva);

    /**
     * Elimina una reserva por su ID.
     * Retorna el número de filas afectadas.
     */
    int delete(int id_reserva);

    /**
     * Cuenta el total de reservas.
     */
    int count();
}
