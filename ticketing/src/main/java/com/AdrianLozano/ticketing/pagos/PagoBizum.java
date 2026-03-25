package com.AdrianLozano.ticketing.pagos;

import com.AdrianLozano.ticketing.excepciones.PagoRechazadoException;


public class PagoBizum extends Pago {

    private String telefono;

    public PagoBizum(String idPago, String telefono) {
        super(idPago);
        this.telefono = telefono;
    }

    @Override
    public void procesarPago(double importe) throws PagoRechazadoException {
        System.out.println("Conectando con Bizum...");
        // Aquí iría la lógica real. Si fallara:
        // throw new excepciones.PagoRechazadoException("Bizum");
        System.out.println("Pago de " + importe + "€ procesado con Bizum al " + telefono);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
