package com.casanova.controller;

import com.casanova.dao.PropiedadesDAO;
import com.casanova.dao.ReservasDAO;
import com.casanova.model.PropiedadesCasanova;
import com.casanova.model.ReservasCasanova;
import com.casanova.model.UsuarioCasanova;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/anfitrion")
public class AnfitrionController {

    private final PropiedadesDAO propiedadesDAO;
    private final ReservasDAO reservasDAO;

    public AnfitrionController(PropiedadesDAO propiedadesDAO, ReservasDAO reservasDAO) {
        this.propiedadesDAO = propiedadesDAO;
        this.reservasDAO = reservasDAO;
    }

    /**
     * Lee classpath:/static/img/ y devuelve todos los nombres de imagen.
     * Así el formulario siempre refleja exactamente lo que tienes en esa carpeta.
     */
    private List<String> getImagenesDisponibles() {
        List<String> imagenes = new ArrayList<>();
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:/static/img/*.{jpg,jpeg,png,webp,gif}");
            for (Resource r : resources) {
                imagenes.add(r.getFilename());
            }
        } catch (IOException e) {
            // si falla devolvemos lista vacía; el formulario lo indica al usuario
        }
        return imagenes;
    }

    // PANEL
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";
        model.addAttribute("propiedades", propiedadesDAO.findByUsuario(usuario.getId_usuario()));
        return "anfitrion/panel";
    }

    // NUEVA PROPIEDAD - GET
    @GetMapping("/nueva-propiedad")
    public String nuevaPropiedadForm(HttpSession session, Model model) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";
        model.addAttribute("imagenesDisponibles", getImagenesDisponibles());
        return "anfitrion/nueva-propiedad";
    }

    // NUEVA PROPIEDAD - POST
    @PostMapping("/nueva-propiedad")
    public String nuevaPropiedad(@RequestParam String titulo,
                                  @RequestParam String descripcion,
                                  @RequestParam String direccion,
                                  @RequestParam String ciudad,
                                  @RequestParam String pais,
                                  @RequestParam double precio_noche,
                                  @RequestParam int capacidad,
                                  @RequestParam String tipo_operacion,
                                  @RequestParam String tipo_propiedad,
                                  @RequestParam(required = false) String foto,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";

        PropiedadesCasanova p = new PropiedadesCasanova();
        p.setId_usuario(usuario.getId_usuario());
        p.setTitulo(titulo);
        p.setDescripcion(descripcion);
        p.setDireccion(direccion);
        p.setCiudad(ciudad);
        p.setPais(pais);
        p.setPrecio_noche(precio_noche);
        p.setCapacidad(capacidad);
        p.setTipo_operacion(tipo_operacion);
        p.setTipo_propiedad(tipo_propiedad);
        p.setActiva(true);
        p.setFoto(foto != null && !foto.isBlank() ? foto : "default.jpg");

        propiedadesDAO.insert(p);
        redirectAttributes.addFlashAttribute("exito", "Propiedad publicada correctamente.");
        return "redirect:/anfitrion/panel";
    }

    // EDITAR PROPIEDAD - GET
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable int id, HttpSession session, Model model) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";

        Optional<PropiedadesCasanova> propOpt = propiedadesDAO.findById(id);
        if (propOpt.isEmpty() || propOpt.get().getId_usuario() != usuario.getId_usuario()) return "redirect:/anfitrion/panel";

        model.addAttribute("propiedad", propOpt.get());
        model.addAttribute("imagenesDisponibles", getImagenesDisponibles());
        return "anfitrion/editar-propiedad";
    }

    // EDITAR PROPIEDAD - POST
    @PostMapping("/editar/{id}")
    public String editarPropiedad(@PathVariable int id,
                                   @RequestParam String titulo,
                                   @RequestParam String descripcion,
                                   @RequestParam String direccion,
                                   @RequestParam String ciudad,
                                   @RequestParam String pais,
                                   @RequestParam double precio_noche,
                                   @RequestParam int capacidad,
                                   @RequestParam String tipo_operacion,
                                   @RequestParam String tipo_propiedad,
                                   @RequestParam(required = false) String foto,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";

        String fotoFinal = (foto != null && !foto.isBlank()) ? foto :
                propiedadesDAO.findById(id).map(PropiedadesCasanova::getFoto).orElse("default.jpg");

        PropiedadesCasanova p = new PropiedadesCasanova();
        p.setId_propiedad(id);
        p.setTitulo(titulo);
        p.setDescripcion(descripcion);
        p.setDireccion(direccion);
        p.setCiudad(ciudad);
        p.setPais(pais);
        p.setPrecio_noche(precio_noche);
        p.setCapacidad(capacidad);
        p.setTipo_operacion(tipo_operacion);
        p.setTipo_propiedad(tipo_propiedad);
        p.setFoto(fotoFinal);

        propiedadesDAO.update(p);
        redirectAttributes.addFlashAttribute("exito", "Propiedad actualizada correctamente.");
        return "redirect:/anfitrion/panel";
    }

    // ELIMINAR
    @PostMapping("/eliminar/{id}")
    public String eliminarPropiedad(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";
        propiedadesDAO.delete(id);
        redirectAttributes.addFlashAttribute("exito", "Propiedad eliminada.");
        return "redirect:/anfitrion/panel";
    }

    // RESERVAS DE UNA PROPIEDAD
    @GetMapping("/reservas/{id_propiedad}")
    public String verReservas(@PathVariable int id_propiedad, HttpSession session, Model model) {
        UsuarioCasanova usuario = (UsuarioCasanova) session.getAttribute("usuarioLogueado");
        if (usuario == null || usuario.getTipo() != UsuarioCasanova.Tipo.anfitrion) return "redirect:/inicio";
        model.addAttribute("reservas", reservasDAO.findByPropiedad(id_propiedad));
        model.addAttribute("id_propiedad", id_propiedad);
        return "anfitrion/reservas-propiedad";
    }

    @PostMapping("/reservas/{id_reserva}/estado")
    public String cambiarEstadoReserva(@PathVariable int id_reserva,
                                        @RequestParam String estado,
                                        @RequestParam int id_propiedad,
                                        RedirectAttributes redirectAttributes) {
        reservasDAO.updateEstado(id_reserva, ReservasCasanova.Estado.valueOf(estado));
        redirectAttributes.addFlashAttribute("exito", "Estado actualizado.");
        return "redirect:/anfitrion/reservas/" + id_propiedad;
    }
}
