package com.casanova.dao;

import com.casanova.model.PropiedadesCasanova;
import java.util.List;
import java.util.Optional;

public interface PropiedadesDAO {

    /**
     * Obtiene todas las propiedades activas.
     */
    List<PropiedadesCasanova> findAll();

    /**
     * Busca una propiedad por su ID.
     */
    Optional<PropiedadesCasanova> findById(int id_propiedad);

    /**
     * Obtiene propiedades de un usuario anfitrión concreto.
     */
    List<PropiedadesCasanova> findByUsuario(int id_usuario);

    /**
     * Busca propiedades por ciudad.
     */
    List<PropiedadesCasanova> findByCiudad(String ciudad);

    /**
     * Busca propiedades por tipo de operación (compra/alquiler).
     */
    List<PropiedadesCasanova> findByTipoOperacion(String tipo_operacion);

    /**
     * Búsqueda avanzada con filtros opcionales.
     */
    List<PropiedadesCasanova> buscar(String ciudad, String tipo_propiedad,
                                     String tipo_operacion, Double precioMin, Double precioMax);

    /**
     * Inserta una nueva propiedad.
     * Retorna el número de filas afectadas.
     */
    int insert(PropiedadesCasanova propiedad);

    /**
     * Actualiza los datos de una propiedad existente.
     * Retorna el número de filas afectadas.
     */
    int update(PropiedadesCasanova propiedad);

    /**
     * Elimina (o desactiva) una propiedad por su ID.
     * Retorna el número de filas afectadas.
     */
    int delete(int id_propiedad);

    /**
     * Cuenta el total de propiedades activas.
     */
    int count();
}
