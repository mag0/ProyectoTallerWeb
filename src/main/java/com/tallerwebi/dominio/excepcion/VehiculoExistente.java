package com.tallerwebi.dominio.excepcion;

public class VehiculoExistente extends RuntimeException{
    public VehiculoExistente(String mensaje) {
        super(mensaje);
    }
}
