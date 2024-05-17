package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioVehiculoImpl implements ServicioVehiculo {
    private RepositorioVehiculo vehiculoRepository;

    @Autowired
    public ServicioVehiculoImpl(RepositorioVehiculo vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public Vehiculo buscarVehiculo(Integer id) throws Exception {
        Vehiculo vehiculo = vehiculoRepository.buscar(id);
        return vehiculo;
    }
}
