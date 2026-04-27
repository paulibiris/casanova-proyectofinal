package com.casanova.controller;

import com.casanova.dao.ComentariosDAO;
import com.casanova.dao.PropiedadesDAO;
import com.casanova.dao.ReservasDAO;
import com.casanova.model.ComentariosCasanova;
import com.casanova.model.PropiedadesCasanova;
import com.casanova.model.ReservasCasanova;
import com.casanova.model.UsuarioCasanova;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/propiedades")
public class PropiedadesController {

    private final PropiedadesDAO propiedadesDAO;
    private final ComentariosDAO comentariosDAO;
    private final ReservasDAO reservasDAO;

    public PropiedadesController(PropiedadesDAO propiedadesDAO,
                                  ComentariosDAO comentariosDAO,
                                  ReservasDAO reservasDAO) {
        this.propiedadesDAO = propiedadesDAO;
        this.comentariosDAO = comentariosDAO;
        this.reservasDAO = reservasDAO;
    }

    // ==============================
    // LISTADO: COMPRA
    // ==============================

    @GetMapping("/compra")
    public String compra(@RequestParam(required = false) String ciudad,
                         @RequestParam(required = false) String tipo_propiedad,
                         @RequestParam(required = false) Double precioMin,
                         @RequestParam(required = false) Double precioMax,
                         Model model) {

        List<PropiedadesCasanova> propiedades = propiedadesDAO.buscar(ciudad, tipo_propiedad, "compra", precioMin, precioMax);
        model.addAttribute("propiedades", propiedades);
        model.addAttribute("tipoOperacion", "Compra");
        return "compra";
    }

    // ==============================
    // LISTADO: ALQUILER
    // ==============================

    @GetMapping("/alquiler")
    public String alquiler(@RequestParam(required = false) String ciudad,
                           @RequestParam(required = false) String tipo_propiedad,
                           @RequestParam(required = false) Double precioMin,
                           @RequestParam(required = false) Double precioMax,
                           Model model) {

        List<PropiedadesCasanova> propiedades = propiedadesDAO.buscar(ciudad, tipo_propiedad, "alquiler", precioMin, precioMax);
        model.addAttribute("propiedades", propiedades);
        model.addAttribute("tipoOperacion", "Alquiler");
        return "alquiler";
    }

    // ==============================
    // DETALLE DE PROPIEDAD
    // ==============================

    @GetMapping("/{id}")
    public String detalle(@PathVariable int id, Model model) {
        Optional<PropiedadesCasanova> propiedad = propiedadesDAO.findById(id);

        if (propiedad.isEmpty()) {
            return "redirect:/inicio";
        }

        List<ComentariosCasanova> comentarios = comentariosDAO.findByPropiedad(id);
        Double puntuacionMedia = comentariosDAO.getPuntuacionMedia(id);

        model.addAttribute("propiedad", propiedad.get());
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("puntuacionMedia", puntuacionMedia != null ? String.format("%.1f", puntuacionMedia) : "Sin valoraciones");
        model.addAttribute("numComentarios", comentarios.size());
        return "detalle-propiedad";
    }

    // ==============================
    // AÑADIR COMENTARIO
    // ==============================

    @PostMapping("/{id}/comentario")
    public String agregarComentario(@PathVariable int id,
                                    @RequestParam String comentario,
                                    @RequestParam int puntuacion,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {

        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para comentar.");
            return "redirect:/propiedades/" + id;
        }

        ComentariosCasanova nuevo = new ComentariosCasanova();
        nuevo.setId_propiedad(id);
        nuevo.setId_usuario(usuario.getId_usuario());
        nuevo.setComentario(comentario);
        nuevo.setPuntuacion(puntuacion);
        nuevo.setFecha(LocalDate.now());

        comentariosDAO.insert(nuevo);
        redirectAttributes.addFlashAttribute("exito", "Comentario añadido correctamente.");
        return "redirect:/propiedades/" + id;
    }

    // ==============================
    // REALIZAR RESERVA
    // ==============================

    @PostMapping("/{id}/reservar")
    public String realizarReserva(@PathVariable int id,
                                  @RequestParam String fecha_inicio,
                                  @RequestParam String fecha_fin,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para reservar.");
            return "redirect:/propiedades/" + id;
        }

        Optional<PropiedadesCasanova> propOpt = propiedadesDAO.findById(id);
        if (propOpt.isEmpty()) {
            return "redirect:/inicio";
        }

        // Calcular precio total
        LocalDate inicio = LocalDate.parse(fecha_inicio);
        LocalDate fin = LocalDate.parse(fecha_fin);
        // long noches = ChronoUnit.DAYS.between(inicio, fin);
        // double precioTotal = noches * propOpt.get().getPrecio_noche();
        long noches = ChronoUnit.DAYS.between(inicio, fin);
        double precioPorDia = propOpt.get().getPrecio_noche() / 30.0;
        double precioTotal = noches * precioPorDia;

        ReservasCasanova reserva = new ReservasCasanova();
        reserva.setId_propiedad(id);
        reserva.setId_usuario(usuario.getId_usuario());
        reserva.setFecha_inicio(fecha_inicio);
        reserva.setFecha_fin(fecha_fin);
        reserva.setPrecio_total(precioTotal);
        reserva.setEstado(ReservasCasanova.Estado.pendiente);
        reserva.setFecha_reserva(LocalDate.now());

        reservasDAO.insert(reserva);
        redirectAttributes.addFlashAttribute("exito", "¡Reserva realizada correctamente! Precio total: €" + String.format("%.2f", precioTotal));
        return "redirect:/propiedades/" + id;
    }

    // ==============================
    // BUSCADOR GENERAL
    // ==============================

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String ciudad,
                         @RequestParam(required = false) String tipo_propiedad,
                         @RequestParam(required = false) String operacion,
                         @RequestParam(required = false) Double precioMin,
                         @RequestParam(required = false) Double precioMax,
                         Model model) {

        List<PropiedadesCasanova> propiedades = propiedadesDAO.buscar(ciudad, tipo_propiedad, operacion, precioMin, precioMax);
        model.addAttribute("propiedades", propiedades);
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("operacion", operacion);
        return "resultados-busqueda";
    }
}
