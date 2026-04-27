package com.casanova.controller;

import com.casanova.dao.ReservasDAO;
import com.casanova.model.ReservasCasanova;
import com.casanova.model.UsuarioCasanova;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/huesped")
public class HuespedController {

    private final ReservasDAO reservasDAO;

    public HuespedController(ReservasDAO reservasDAO) {
        this.reservasDAO = reservasDAO;
    }

    // ==============================
    // MIS RESERVAS
    // ==============================

    @GetMapping("/mis-reservas")
    public String misReservas(HttpSession session, Model model) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.huesped) {
            return "redirect:/inicio";
        }

        List<ReservasCasanova> reservas = reservasDAO.findByUsuario(usuario.getId_usuario());
        model.addAttribute("reservas", reservas);
        return "huesped/mis-reservas";
    }

    // ==============================
    // CANCELAR RESERVA
    // ==============================

    @PostMapping("/cancelar/{id_reserva}")
    public String cancelarReserva(@PathVariable int id_reserva,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/inicio";
        }

        reservasDAO.updateEstado(id_reserva, ReservasCasanova.Estado.cancelada);
        redirectAttributes.addFlashAttribute("exito", "Reserva cancelada correctamente.");
        return "redirect:/huesped/mis-reservas";
    }
}
