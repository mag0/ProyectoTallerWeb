package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioEliminarVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioEliminarVehiculoImpl implements ServicioEliminarVehiculo {

    private final RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioEliminarVehiculoImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public void eliminarVehiculo(Vehiculo vehiculo, FlotaDeVehiculos flotaDeVehiculos) {
        // Eliminar vehículo de la base de datos
        repositorioVehiculo.eliminar(vehiculo);

        // Eliminar vehículo de la flota
        flotaDeVehiculos.eliminarVehiculo(vehiculo);
    }
}

