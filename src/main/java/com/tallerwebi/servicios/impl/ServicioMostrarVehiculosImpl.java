package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.dominio.excepcion.VehiculoInexistente;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioMostrarVehiculosImpl implements ServicioMostrarVehiculos {

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioMostrarVehiculosImpl(RepositorioVehiculo repositorioVehiculo, RepositorioUsuario repositorioUsuario) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioUsuario = repositorioUsuario;
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
    public List<Vehiculo> obtenerVehiculosDisponiblesPorUsuario() {
        Long idUsuario = repositorioUsuario.buscarUsuarioActivo().getId();
        List<Vehiculo> vehiculos = repositorioVehiculo.buscarTodosPorUsuario(idUsuario);

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
