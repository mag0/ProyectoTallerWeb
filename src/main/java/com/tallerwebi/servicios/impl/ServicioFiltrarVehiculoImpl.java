package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoExisteFiltrado;
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

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioVehiculo repositorioVehiculo;

    public ServicioFiltrarVehiculoImpl(RepositorioVehiculo repositorioVehiculo, RepositorioUsuario repositorioUsuario) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Vehiculo> obtenerVehiculosFiltrados(String tipo, String marca, String modelo) {
        String tipoABuscar = tipo.toLowerCase();
        String marcaABuscar = marca.toLowerCase();
        String modeloABuscar = modelo.toLowerCase();

        if (tipoABuscar.isEmpty () && marcaABuscar.isEmpty() && modeloABuscar.isEmpty()) {
            throw new NoExisteFiltrado();
        }

        Long idUsuario = repositorioUsuario.buscarUsuarioActivo().getId();
        List<Vehiculo> vehiculos = repositorioVehiculo.buscarTodosPorUsuario(idUsuario);
        List<Vehiculo> vehiculosFiltrados = new ArrayList<>();

        vehiculos.forEach(vehiculo -> {
            // Filtrar por tipo o marca
            if ((!tipoABuscar.isEmpty() && vehiculo.getTipo().toLowerCase().contains(tipoABuscar)) ||
                    (!marcaABuscar.isEmpty() && vehiculo.getMarca().toLowerCase().contains(marcaABuscar)) ||
                    (!modeloABuscar.isEmpty() && vehiculo.getModelo().toLowerCase().contains(modeloABuscar))) {
                vehiculosFiltrados.add(vehiculo);
            }
        });
        if (vehiculosFiltrados.isEmpty()) {
            throw new NoHayVehiculosEnLaFlota();
        }


        return vehiculosFiltrados;
    }
}
