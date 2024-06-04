package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.dominio.excepcion.VehiculoInexistente;
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

    @Autowired
    public ServicioMostrarVehiculosImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public List<Vehiculo> obtenerVehiculosDisponibles() {
        List<Vehiculo> vehiculos = repositorioVehiculo.buscarTodos();
        if (vehiculos.isEmpty()) {
            throw new NoHayVehiculosEnLaFlota();
        }
        return vehiculos;
    }

    @Override
    public Vehiculo obtenerVehiculo(Long id) {
        return repositorioVehiculo.buscar(id);
    }
}
