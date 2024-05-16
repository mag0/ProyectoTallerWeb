package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioVehiculoImpl implements ServicioVehiculo {

    public ServicioVehiculoImpl() {}

    @Override
    public Vehiculo buscarVehiculo(Integer id) throws Exception {
        return null;
    }
}
