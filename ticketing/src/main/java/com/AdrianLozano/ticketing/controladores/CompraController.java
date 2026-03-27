package com.AdrianLozano.ticketing.controladores;

import com.AdrianLozano.ticketing.modelo.Evento;
import com.AdrianLozano.ticketing.modelo.Sesion;
import com.AdrianLozano.ticketing.pedidos.Pedido;
import com.AdrianLozano.ticketing.servicios.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompraController {

    @Autowired
    private TicketingService ticketingService;

    @GetMapping("/comprar/{idEvento}/{idSesion}")
    public String mostrarFormulario(@PathVariable String idEvento,
                                    @PathVariable String idSesion,
                                    Model model) {

        Evento evento = ticketingService.buscarEventoPorId(idEvento);
        Sesion sesion = ticketingService.buscarSesion(idEvento, idSesion);

        if (evento == null || sesion == null) return "redirect:/";

        model.addAttribute("evento", evento);
        model.addAttribute("sesion", sesion);
        return "compra";
    }

    @PostMapping("/comprar/procesar")
    public String procesarCompra(@RequestParam String idEvento,
                                  @RequestParam String idSesion,
                                  @RequestParam int cantidad,
                                  @RequestParam String email,
                                  @RequestParam String metodoPago,
                                  @RequestParam String datosPago,
                                  Model model) {
        try {
            Pedido pedido = ticketingService.procesarCompra(
                idEvento, idSesion, cantidad, email, metodoPago, datosPago);

            model.addAttribute("pedido", pedido);
            return "resultado";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("idEvento", idEvento);
            model.addAttribute("idSesion", idSesion);
            return "compra";
        }
    }
}