package com.AdrianLozano.ticketing.controladores;

import com.AdrianLozano.ticketing.modelo.Evento;
import com.AdrianLozano.ticketing.utilidades.AccesoDatos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;

@Controller
public class EventoController {

    private AccesoDatos db = new AccesoDatos();

    @GetMapping("/evento/{id}")
    public String mostrarEvento(@PathVariable String id, Model model) {
        ArrayList<Evento> catalogo = db.cargarDatosDesdeNube();
        
        Evento eventoEncontrado = null;
        for (Evento e : catalogo) {
            if (e.getId().equalsIgnoreCase(id)) {
                eventoEncontrado = e;
                break;
            }
        }

        if (eventoEncontrado == null) {
            return "redirect:/";
        }

        model.addAttribute("evento", eventoEncontrado);
        return "evento";
    }
}