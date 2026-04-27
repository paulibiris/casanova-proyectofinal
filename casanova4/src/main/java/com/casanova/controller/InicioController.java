package com.casanova.controller;

import com.casanova.dao.PropiedadesDAO;
import com.casanova.dao.UsuarioDAO;
import com.casanova.model.PropiedadesCasanova;
import com.casanova.model.UsuarioCasanova;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class InicioController {

    private final PropiedadesDAO propiedadesDAO;
    private final UsuarioDAO usuarioDAO;

    public InicioController(PropiedadesDAO propiedadesDAO, UsuarioDAO usuarioDAO) {
        this.propiedadesDAO = propiedadesDAO;
        this.usuarioDAO = usuarioDAO;
    }

    // ==============================
    // INICIO
    // ==============================

    @GetMapping({"/", "/inicio"})
    public String inicio(Model model) {
        // Obtener propiedades destacadas (máximo 6)
        List<PropiedadesCasanova> propiedades = propiedadesDAO.findAll();
        int limite = Math.min(propiedades.size(), 6);
        model.addAttribute("propiedadesDestacadas", propiedades.subList(0, limite));
        return "inicio";
    }

    // ==============================
    // LOGIN
    // ==============================

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String tipo,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        Optional<UsuarioCasanova> usuario = usuarioDAO.login(email, password, tipo);

        if (usuario.isPresent()) {
            session.setAttribute("usuarioLogueado", usuario.get());
            return "redirect:/inicio";
        } else {
            redirectAttributes.addFlashAttribute("errorLogin", "Credenciales incorrectas o tipo de usuario no válido.");
            return "redirect:/inicio";
        }
    }

    // ==============================
    // LOGOUT
    // ==============================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/inicio";
    }

    // ==============================
    // REGISTRO
    // ==============================

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
                           @RequestParam String email,
                           @RequestParam String telefono,
                           @RequestParam String password,
                           @RequestParam String tipo,
                           RedirectAttributes redirectAttributes) {

        // Comprobar si el email ya existe
        if (usuarioDAO.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("errorRegistro", "Ya existe una cuenta con ese email.");
            return "redirect:/inicio";
        }

        UsuarioCasanova nuevo = new UsuarioCasanova();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setTelefono(telefono);
        nuevo.setPassword_hash(password); // En producción: usar BCrypt
        nuevo.setTipo(UsuarioCasanova.Tipo.valueOf(tipo));

        usuarioDAO.insert(nuevo);
        redirectAttributes.addFlashAttribute("exitoRegistro", "Cuenta creada correctamente. Ya puedes iniciar sesión.");
        return "redirect:/inicio";
    }
}
