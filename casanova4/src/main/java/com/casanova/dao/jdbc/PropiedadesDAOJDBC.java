package com.casanova.dao.jdbc;

import com.casanova.dao.PropiedadesDAO;
import com.casanova.model.PropiedadesCasanova;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PropiedadesDAOJDBC implements PropiedadesDAO {

    private final JdbcTemplate jdbcTemplate;

    public PropiedadesDAOJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==============================
    // ROW MAPPER
    // ==============================

    private final RowMapper<PropiedadesCasanova> propiedadRowMapper = (rs, rowNum) -> {
        PropiedadesCasanova p = new PropiedadesCasanova();
        p.setId_propiedad(rs.getInt("id_propiedad"));
        p.setId_usuario(rs.getInt("id_usuario"));
        p.setTitulo(rs.getString("titulo"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setDireccion(rs.getString("direccion"));
        p.setCiudad(rs.getString("ciudad"));
        p.setPais(rs.getString("pais"));
        p.setPrecio_noche(rs.getDouble("precio_noche"));
        p.setCapacidad(rs.getInt("capacidad"));
        p.setTipo_operacion(rs.getString("tipo_operacion"));
        p.setTipo_propiedad(rs.getString("tipo_propiedad"));
        p.setActiva(rs.getBoolean("activa"));
        p.setFoto(rs.getString("foto"));
        return p;
    };

    // ==============================
    // IMPLEMENTACIONES
    // ==============================

    @Override
    public List<PropiedadesCasanova> findAll() {
        String sql = "SELECT * FROM propiedades WHERE activa = TRUE ORDER BY id_propiedad DESC";
        return jdbcTemplate.query(sql, propiedadRowMapper);
    }

    @Override
    public Optional<PropiedadesCasanova> findById(int id_propiedad) {
        String sql = "SELECT * FROM propiedades WHERE id_propiedad = ?";
        List<PropiedadesCasanova> resultado = jdbcTemplate.query(sql, propiedadRowMapper, id_propiedad);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public List<PropiedadesCasanova> findByUsuario(int id_usuario) {
        String sql = "SELECT * FROM propiedades WHERE id_usuario = ? AND activa = TRUE ORDER BY id_propiedad DESC";
        return jdbcTemplate.query(sql, propiedadRowMapper, id_usuario);
    }

    @Override
    public List<PropiedadesCasanova> findByCiudad(String ciudad) {
        String sql = "SELECT * FROM propiedades WHERE ciudad LIKE ? AND activa = TRUE";
        return jdbcTemplate.query(sql, propiedadRowMapper, "%" + ciudad + "%");
    }

    @Override
    public List<PropiedadesCasanova> findByTipoOperacion(String tipo_operacion) {
        String sql = "SELECT * FROM propiedades WHERE tipo_operacion = ? AND activa = TRUE ORDER BY precio_noche";
        return jdbcTemplate.query(sql, propiedadRowMapper, tipo_operacion);
    }

    @Override
    public List<PropiedadesCasanova> buscar(String ciudad, String tipo_propiedad,
                                             String tipo_operacion, Double precioMin, Double precioMax) {
        StringBuilder sql = new StringBuilder("SELECT * FROM propiedades WHERE activa = TRUE");
        List<Object> params = new ArrayList<>();

        if (ciudad != null && !ciudad.isEmpty()) {
            sql.append(" AND ciudad LIKE ?");
            params.add("%" + ciudad + "%");
        }
        if (tipo_propiedad != null && !tipo_propiedad.isEmpty()) {
            sql.append(" AND tipo_propiedad = ?");
            params.add(tipo_propiedad);
        }
        if (tipo_operacion != null && !tipo_operacion.isEmpty()) {
            sql.append(" AND tipo_operacion = ?");
            params.add(tipo_operacion);
        }
        if (precioMin != null) {
            sql.append(" AND precio_noche >= ?");
            params.add(precioMin);
        }
        if (precioMax != null) {
            sql.append(" AND precio_noche <= ?");
            params.add(precioMax);
        }

        sql.append(" ORDER BY precio_noche");
        return jdbcTemplate.query(sql.toString(), propiedadRowMapper, params.toArray());
    }

    @Override
    public int insert(PropiedadesCasanova propiedad) {
        String sql = "INSERT INTO propiedades (id_usuario, titulo, descripcion, direccion, ciudad, pais, " +
                     "precio_noche, capacidad, tipo_operacion, tipo_propiedad, activa, foto) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                propiedad.getId_usuario(),
                propiedad.getTitulo(),
                propiedad.getDescripcion(),
                propiedad.getDireccion(),
                propiedad.getCiudad(),
                propiedad.getPais(),
                propiedad.getPrecio_noche(),
                propiedad.getCapacidad(),
                propiedad.getTipo_operacion(),
                propiedad.getTipo_propiedad(),
                propiedad.isActiva(),
                propiedad.getFoto() != null ? propiedad.getFoto() : "default.jpg"
        );
    }

    @Override
    public int update(PropiedadesCasanova propiedad) {
        String sql = "UPDATE propiedades SET titulo=?, descripcion=?, direccion=?, ciudad=?, pais=?, " +
                     "precio_noche=?, capacidad=?, tipo_operacion=?, tipo_propiedad=?, foto=? WHERE id_propiedad=?";
        return jdbcTemplate.update(sql,
                propiedad.getTitulo(),
                propiedad.getDescripcion(),
                propiedad.getDireccion(),
                propiedad.getCiudad(),
                propiedad.getPais(),
                propiedad.getPrecio_noche(),
                propiedad.getCapacidad(),
                propiedad.getTipo_operacion(),
                propiedad.getTipo_propiedad(),
                propiedad.getFoto(),
                propiedad.getId_propiedad()
        );
    }

    @Override
    public int delete(int id_propiedad) {
        // Borrado lógico: se desactiva la propiedad en lugar de eliminarla
        String sql = "UPDATE propiedades SET activa = FALSE WHERE id_propiedad = ?";
        return jdbcTemplate.update(sql, id_propiedad);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM propiedades WHERE activa = TRUE";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }
}
