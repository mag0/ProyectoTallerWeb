package com.tallerwebi.dominio.excepcion;

public class DatosIncompletos extends RuntimeException{
    public DatosIncompletos(String mensaje) {
        super(mensaje);
    }
}
