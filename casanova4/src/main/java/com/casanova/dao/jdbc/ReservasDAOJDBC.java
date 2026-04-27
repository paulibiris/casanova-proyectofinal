package com.casanova.dao.jdbc;

import com.casanova.dao.ReservasDAO;
import com.casanova.model.ReservasCasanova;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservasDAOJDBC implements ReservasDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservasDAOJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==============================
    // ROW MAPPER
    // ==============================

    private final RowMapper<ReservasCasanova> reservaRowMapper = (rs, rowNum) -> {
        ReservasCasanova r = new ReservasCasanova();
        r.setId_reserva(rs.getInt("id_reserva"));
        r.setId_propiedad(rs.getInt("id_propiedad"));
        r.setId_usuario(rs.getInt("id_usuario"));
        r.setFecha_inicio(rs.getString("fecha_inicio"));
        r.setFecha_fin(rs.getString("fecha_fin"));
        r.setPrecio_total(rs.getDouble("precio_total"));
        r.setEstado(ReservasCasanova.Estado.valueOf(rs.getString("estado")));

        // fecha_reserva puede ser null en algunos drivers
        java.sql.Date fechaReserva = rs.getDate("fecha_reserva");
        if (fechaReserva != null) {
            r.setFecha_reserva(fechaReserva.toLocalDate());
        } else {
            r.setFecha_reserva(LocalDate.now());
        }
        return r;
    };

    // ==============================
    // IMPLEMENTACIONES
    // ==============================

    @Override
    public List<ReservasCasanova> findAll() {
        String sql = "SELECT * FROM reservas ORDER BY fecha_reserva DESC";
        return jdbcTemplate.query(sql, reservaRowMapper);
    }

    @Override
    public Optional<ReservasCasanova> findById(int id_reserva) {
        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";
        List<ReservasCasanova> resultado = jdbcTemplate.query(sql, reservaRowMapper, id_reserva);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public List<ReservasCasanova> findByUsuario(int id_usuario) {
        String sql = "SELECT * FROM reservas WHERE id_usuario = ? ORDER BY fecha_reserva DESC";
        return jdbcTemplate.query(sql, reservaRowMapper, id_usuario);
    }

    @Override
    public List<ReservasCasanova> findByPropiedad(int id_propiedad) {
        String sql = "SELECT * FROM reservas WHERE id_propiedad = ? ORDER BY fecha_inicio";
        return jdbcTemplate.query(sql, reservaRowMapper, id_propiedad);
    }

    @Override
    public List<ReservasCasanova> findByEstado(ReservasCasanova.Estado estado) {
        String sql = "SELECT * FROM reservas WHERE estado = ? ORDER BY fecha_reserva DESC";
        return jdbcTemplate.query(sql, reservaRowMapper, estado.name());
    }

    @Override
    public int insert(ReservasCasanova reserva) {
        String sql = "INSERT INTO reservas (id_propiedad, id_usuario, fecha_inicio, fecha_fin, " +
                     "precio_total, estado, fecha_reserva) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                reserva.getId_propiedad(),
                reserva.getId_usuario(),
                reserva.getFecha_inicio(),
                reserva.getFecha_fin(),
                reserva.getPrecio_total(),
                reserva.getEstado() != null ? reserva.getEstado().name() : ReservasCasanova.Estado.pendiente.name(),
                reserva.getFecha_reserva() != null ? reserva.getFecha_reserva() : LocalDate.now()
        );
    }

    @Override
    public int updateEstado(int id_reserva, ReservasCasanova.Estado nuevoEstado) {
        String sql = "UPDATE reservas SET estado = ? WHERE id_reserva = ?";
        return jdbcTemplate.update(sql, nuevoEstado.name(), id_reserva);
    }

    @Override
    public int update(ReservasCasanova reserva) {
        String sql = "UPDATE reservas SET id_propiedad=?, id_usuario=?, fecha_inicio=?, " +
                     "fecha_fin=?, precio_total=?, estado=? WHERE id_reserva=?";
        return jdbcTemplate.update(sql,
                reserva.getId_propiedad(),
                reserva.getId_usuario(),
                reserva.getFecha_inicio(),
                reserva.getFecha_fin(),
                reserva.getPrecio_total(),
                reserva.getEstado().name(),
                reserva.getId_reserva()
        );
    }

    @Override
    public int delete(int id_reserva) {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";
        return jdbcTemplate.update(sql, id_reserva);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM reservas";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }
}
