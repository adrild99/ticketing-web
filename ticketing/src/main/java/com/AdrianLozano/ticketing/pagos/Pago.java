package com.AdrianLozano.ticketing.pagos;

import java.time.LocalDateTime;
import com.AdrianLozano.ticketing.excepciones.PagoRechazadoException;

public abstract class Pago implements Pagable {

    private String idPago;
    private LocalDateTime fecha;

    public Pago(String idPago) {
        this.idPago = idPago;
        this.fecha = LocalDateTime.now();
    }

    public abstract void procesarPago(double importe) throws PagoRechazadoException;

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

}
