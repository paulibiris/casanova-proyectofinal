package com.casanova.controller;

import com.casanova.dao.ComentariosDAO;
import com.casanova.dao.PropiedadesDAO;
import com.casanova.dao.ReservasDAO;
import com.casanova.dao.UsuarioDAO;
import com.casanova.model.UsuarioCasanova;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioDAO usuarioDAO;
    private final PropiedadesDAO propiedadesDAO;
    private final ReservasDAO reservasDAO;
    private final ComentariosDAO comentariosDAO;

    public AdminController(UsuarioDAO usuarioDAO, PropiedadesDAO propiedadesDAO,
                            ReservasDAO reservasDAO, ComentariosDAO comentariosDAO) {
        this.usuarioDAO = usuarioDAO;
        this.propiedadesDAO = propiedadesDAO;
        this.reservasDAO = reservasDAO;
        this.comentariosDAO = comentariosDAO;
    }

    // ==============================
    // VERIFICAR ADMIN
    // ==============================

    private boolean esAdmin(HttpSession session) {
        UsuarioCasanova u = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        return u != null && u.getTipo() == UsuarioCasanova.Tipo.admin;
    }

    // ==============================
    // PANEL ADMIN
    // ==============================

    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/inicio";

        model.addAttribute("totalUsuarios", usuarioDAO.count());
        model.addAttribute("totalPropiedades", propiedadesDAO.count());
        model.addAttribute("totalReservas", reservasDAO.count());
        model.addAttribute("usuarios", usuarioDAO.findAll());
        model.addAttribute("propiedades", propiedadesDAO.findAll());
        model.addAttribute("reservas", reservasDAO.findAll());
        return "admin/panel";
    }

    // ==============================
    // ELIMINAR USUARIO
    // ==============================

    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id, HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) return "redirect:/inicio";
        usuarioDAO.delete(id);
        redirectAttributes.addFlashAttribute("exito", "Usuario eliminado.");
        return "redirect:/admin/panel";
    }

    // ==============================
    // ELIMINAR COMENTARIO
    // ==============================

    @PostMapping("/comentarios/eliminar/{id}")
    public String eliminarComentario(@PathVariable int id, HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) return "redirect:/inicio";
        comentariosDAO.delete(id);
        redirectAttributes.addFlashAttribute("exito", "Comentario eliminado.");
        return "redirect:/admin/panel";
    }

    // ==============================
    // ELIMINAR PROPIEDAD (ADMIN)
    // ==============================

    @PostMapping("/propiedades/eliminar/{id}")
    public String eliminarPropiedad(@PathVariable int id, HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) return "redirect:/inicio";
        propiedadesDAO.delete(id);
        redirectAttributes.addFlashAttribute("exito", "Propiedad desactivada.");
        return "redirect:/admin/panel";
    }
}
