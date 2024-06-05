package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioFiltrarVehiculo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioFiltrarVehiculoImpl implements ServicioFiltrarVehiculo {

    private final RepositorioVehiculo repositorioVehiculo;

    public ServicioFiltrarVehiculoImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public List<Vehiculo> obtenerVehiculosFiltrados(String dato) {
        List<Vehiculo> vehiculos = repositorioVehiculo.buscarTodos();
        List<Vehiculo> vehiculosFiltrados = new ArrayList<>();
        String datoABuscar = dato.toLowerCase();

        vehiculos.forEach(vehiculo -> {
            // Filtrar por tipo o marca
            if (vehiculo.getTipo().toLowerCase().contains(datoABuscar) ||
                    vehiculo.getMarca().toLowerCase().contains(datoABuscar) ||
                    vehiculo.getModelo().toLowerCase().contains(datoABuscar) ||
                    (datoABuscar.equals("disponible") && vehiculo.isStatus()) ||
                    (datoABuscar.equals("ocupado") && !vehiculo.isStatus())) {
                vehiculosFiltrados.add(vehiculo);
            }
        });
        if (vehiculosFiltrados.isEmpty()) {
            throw new NoHayVehiculosEnLaFlota();
        }

        return vehiculosFiltrados;
    }
}
