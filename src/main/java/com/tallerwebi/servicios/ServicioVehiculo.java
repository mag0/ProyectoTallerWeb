package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;

public interface ServicioVehiculo {
    Vehiculo buscarVehiculo(Integer id) throws Exception;
}
