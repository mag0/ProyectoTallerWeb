package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Viaje;

import java.util.List;

public interface RepositorioViaje {
    Long guardar(Viaje viaje);
    List<Viaje> getAll();

    List<Viaje> buscarPorVehiculo(String patente);
}
