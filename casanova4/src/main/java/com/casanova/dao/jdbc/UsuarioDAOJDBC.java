package com.casanova.dao.jdbc;

import com.casanova.dao.UsuarioDAO;
import com.casanova.model.UsuarioCasanova;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAOJDBC implements UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAOJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==============================
    // ROW MAPPER
    // ==============================

    private final RowMapper<UsuarioCasanova> usuarioRowMapper = (rs, rowNum) -> {
        UsuarioCasanova u = new UsuarioCasanova();
        u.setId_usuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setTelefono(rs.getString("telefono"));
        u.setPassword_hash(rs.getString("password_hash"));
        u.setTipo(UsuarioCasanova.Tipo.valueOf(rs.getString("tipo")));
        return u;
    };

    // ==============================
    // IMPLEMENTACIONES
    // ==============================

    @Override
    public List<UsuarioCasanova> findAll() {
        String sql = "SELECT * FROM usuarios ORDER BY nombre";
        return jdbcTemplate.query(sql, usuarioRowMapper);
    }

    @Override
    public Optional<UsuarioCasanova> findById(int id_usuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        List<UsuarioCasanova> resultado = jdbcTemplate.query(sql, usuarioRowMapper, id_usuario);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public Optional<UsuarioCasanova> findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        List<UsuarioCasanova> resultado = jdbcTemplate.query(sql, usuarioRowMapper, email);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public Optional<UsuarioCasanova> login(String email, String password, String tipo) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password_hash = ? AND tipo = ?";
        List<UsuarioCasanova> resultado = jdbcTemplate.query(sql, usuarioRowMapper, email, password, tipo);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public int insert(UsuarioCasanova usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, telefono, password_hash, tipo) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getPassword_hash(),
                usuario.getTipo().name()
        );
    }

    @Override
    public int update(UsuarioCasanova usuario) {
        String sql = "UPDATE usuarios SET nombre=?, email=?, telefono=?, password_hash=?, tipo=? WHERE id_usuario=?";
        return jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getPassword_hash(),
                usuario.getTipo().name(),
                usuario.getId_usuario()
        );
    }

    @Override
    public int delete(int id_usuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        return jdbcTemplate.update(sql, id_usuario);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM usuarios";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }
}
