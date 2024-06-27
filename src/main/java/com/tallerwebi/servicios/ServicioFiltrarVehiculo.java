package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;

import java.util.List;

public interface ServicioFiltrarVehiculo {
    List<Vehiculo> obtenerVehiculosFiltrados(String tipo, String marca, String modelo);
}
