package com.tallerwebi.servicios;


import com.tallerwebi.dominio.Viaje;

import java.util.List;
import java.util.Map;

public interface ServicioViaje {
    Long guardar(Viaje viaje);
    List<Viaje> buscarTodos();
    Map<String, Long> obtenerViajesPorVehiculo();
}