package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import java.util.List;

public interface ServicioMostrarVehiculos{
    List<Vehiculo> obtenerVehiculosDisponibles();
    Vehiculo obtenerVehiculo(Long id);
}
