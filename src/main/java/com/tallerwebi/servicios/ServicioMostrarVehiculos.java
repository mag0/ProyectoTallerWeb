package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import java.util.List;

public interface ServicioMostrarVehiculos{
    List<Vehiculo> obtenerVehiculosDisponibles();
    List<Vehiculo> obtenerVehiculosDisponiblesPorUsuario();
    Vehiculo obtenerVehiculo(Long id);
}
