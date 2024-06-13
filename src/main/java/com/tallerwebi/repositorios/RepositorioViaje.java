package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Viaje;

import java.util.List;
import java.util.Map;

public interface RepositorioViaje {
    Long guardar(Viaje viaje);
    List<Viaje> getAll();
    List<Viaje> buscarPorVehiculo(String patente);
    Map<String, Long> contarViajesPorVehiculo();
    Viaje getViaje(Long id);
    void actualizarViaje(Viaje viaje);
}
