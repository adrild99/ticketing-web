package com.AdrianLozano.ticketing.pagos;

import com.AdrianLozano.ticketing.excepciones.PagoRechazadoException;

public interface Pagable {
public abstract void procesarPago(double importe) throws PagoRechazadoException;
}
