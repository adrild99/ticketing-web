package com.AdrianLozano.ticketing.servicios;

import com.AdrianLozano.ticketing.modelo.*;
import com.AdrianLozano.ticketing.pagos.*;
import com.AdrianLozano.ticketing.pedidos.*;
import com.AdrianLozano.ticketing.utilidades.AccesoDatos;
import com.AdrianLozano.ticketing.excepciones.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class TicketingService {

    private AccesoDatos db = new AccesoDatos();

    public Evento buscarEventoPorId(String idEvento) {
        for (Evento e : db.cargarDatosDesdeNube()) {
            if (e.getId().equalsIgnoreCase(idEvento)) return e;
        }
        return null;
    }

    public Sesion buscarSesion(String idEvento, String idSesion) {
        Evento ev = buscarEventoPorId(idEvento);
        if (ev == null) return null;
        return ev.getSesionById(idSesion);
    }

    public Pedido procesarCompra(String idEvento, String idSesion,
                                  int cantidad, String email,
                                  String metodoPago, String datosPago)
            throws AsientoNoDisponibleException, PagoRechazadoException {

        Evento evento = buscarEventoPorId(idEvento);
        Sesion sesion = evento.getSesionById(idSesion);

        if (!sesion.hayDisponibilidad(cantidad)) {
            throw new AsientoNoDisponibleException("No hay suficientes entradas disponibles");
        }

        Carrito carrito = new Carrito();
        ArrayList<Entrada> entradasCompradas = new ArrayList<>();
        double precioBase = 20.0;

        sesion.reservarGeneral(cantidad);
        db.actualizarAforoEnNube(sesion, evento.getId());

        for (int i = 0; i < cantidad; i++) {
            double precioFinal = precioBase * evento.getRecargoBase();
            Entrada e = new Entrada(evento.getId(), sesion.getIdSesion(), null, precioFinal);
            carrito.addEntrada(e);
            entradasCompradas.add(e);
        }

        // Crear pago según método
        Pago pago;
        switch (metodoPago) {
            case "bizum"  -> pago = new PagoBizum("PAGO-1", datosPago);
            case "paypal" -> pago = new PagoPayPal("PAGO-1", datosPago, 1.50);
            default       -> pago = new PagoTarjeta("PAGO-1", datosPago, "", "", "");
        }

        pago.procesarPago(carrito.calcularTotal());

        Pedido pedido = new Pedido(carrito, pago, evento.getNombre());

        db.guardarVentaEnNube(
            pedido.getIdPedido(), evento.getId(), sesion.getIdSesion(),
            cantidad, carrito.calcularTotal(),
            pago.getClass().getSimpleName(), email, "General");

        return pedido;
    }
}