package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioMostrarVehiculosImpl implements ServicioMostrarVehiculos {

    private final RepositorioVehiculo repositorioVehiculo;
    private final FlotaDeVehiculos flotaDeVehiculos;

    @Autowired
    public ServicioMostrarVehiculosImpl(RepositorioVehiculo repositorioVehiculo, FlotaDeVehiculos flotaDeVehiculos) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.flotaDeVehiculos = flotaDeVehiculos;
    }

    @Override
    public List<Vehiculo> mostrarFlota(FlotaDeVehiculos flotaDeVehiculos) {
        List<Vehiculo> vehiculos = obtenerVehiculosDisponibles();
        if (vehiculos.isEmpty()) {
            throw new NoHayVehiculosEnLaFlota();
        }
        flotaDeVehiculos.getVehiculos().clear();
        flotaDeVehiculos.getVehiculos().addAll(vehiculos);
        return vehiculos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return repositorioVehiculo.buscarTodos();
    }
}
