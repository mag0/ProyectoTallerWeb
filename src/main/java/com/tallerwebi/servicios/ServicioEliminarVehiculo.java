package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.FlotaDeVehiculos;

public interface ServicioEliminarVehiculo {
    void eliminarVehiculo(Vehiculo vehiculo, FlotaDeVehiculos flotaDeVehiculos);
}

