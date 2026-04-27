package com.casanova.dao.jdbc;

import com.casanova.dao.ComentariosDAO;
import com.casanova.model.ComentariosCasanova;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ComentariosDAOJDBC implements ComentariosDAO {

    private final JdbcTemplate jdbcTemplate;

    public ComentariosDAOJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==============================
    // ROW MAPPER
    // ==============================

    private final RowMapper<ComentariosCasanova> comentarioRowMapper = (rs, rowNum) -> {
        ComentariosCasanova c = new ComentariosCasanova();
        c.setId_comentario(rs.getInt("id_comentario"));
        c.setId_propiedad(rs.getInt("id_propiedad"));
        c.setId_usuario(rs.getInt("id_usuario"));
        c.setComentario(rs.getString("comentario"));
        c.setPuntuacion(rs.getInt("puntuacion"));

        java.sql.Date fecha = rs.getDate("fecha");
        c.setFecha(fecha != null ? fecha.toLocalDate() : LocalDate.now());
        return c;
    };

    // ==============================
    // IMPLEMENTACIONES
    // ==============================

    @Override
    public List<ComentariosCasanova> findAll() {
        String sql = "SELECT * FROM comentarios ORDER BY fecha DESC";
        return jdbcTemplate.query(sql, comentarioRowMapper);
    }

    @Override
    public Optional<ComentariosCasanova> findById(int id_comentario) {
        String sql = "SELECT * FROM comentarios WHERE id_comentario = ?";
        List<ComentariosCasanova> resultado = jdbcTemplate.query(sql, comentarioRowMapper, id_comentario);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public List<ComentariosCasanova> findByPropiedad(int id_propiedad) {
        String sql = "SELECT * FROM comentarios WHERE id_propiedad = ? ORDER BY fecha DESC";
        return jdbcTemplate.query(sql, comentarioRowMapper, id_propiedad);
    }

    @Override
    public List<ComentariosCasanova> findByUsuario(int id_usuario) {
        String sql = "SELECT * FROM comentarios WHERE id_usuario = ? ORDER BY fecha DESC";
        return jdbcTemplate.query(sql, comentarioRowMapper, id_usuario);
    }

    @Override
    public Double getPuntuacionMedia(int id_propiedad) {
        String sql = "SELECT AVG(puntuacion) FROM comentarios WHERE id_propiedad = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, id_propiedad);
    }

    @Override
    public int insert(ComentariosCasanova comentario) {
        String sql = "INSERT INTO comentarios (id_propiedad, id_usuario, comentario, puntuacion, fecha) " +
                     "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                comentario.getId_propiedad(),
                comentario.getId_usuario(),
                comentario.getComentario(),
                comentario.getPuntuacion(),
                comentario.getFecha() != null ? comentario.getFecha() : LocalDate.now()
        );
    }

    @Override
    public int update(ComentariosCasanova comentario) {
        String sql = "UPDATE comentarios SET comentario=?, puntuacion=? WHERE id_comentario=?";
        return jdbcTemplate.update(sql,
                comentario.getComentario(),
                comentario.getPuntuacion(),
                comentario.getId_comentario()
        );
    }

    @Override
    public int delete(int id_comentario) {
        String sql = "DELETE FROM comentarios WHERE id_comentario = ?";
        return jdbcTemplate.update(sql, id_comentario);
    }

    @Override
    public int countByPropiedad(int id_propiedad) {
        String sql = "SELECT COUNT(*) FROM comentarios WHERE id_propiedad = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, id_propiedad);
        return result != null ? result : 0;
    }
}
