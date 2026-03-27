package com.AdrianLozano.ticketing.controladores;

import com.AdrianLozano.ticketing.modelo.Evento;
import com.AdrianLozano.ticketing.utilidades.AccesoDatos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class CatalogoController {

    private AccesoDatos db = new AccesoDatos();

    @GetMapping("/")
    public String mostrarCatalogo(Model model) {
        ArrayList<Evento> catalogo = db.cargarDatosDesdeNube();
        model.addAttribute("catalogo", catalogo);
        return "catalogo";
    }
}