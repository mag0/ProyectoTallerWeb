package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.Zona;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje viajeRepository;

    public ServicioViajeImpl(RepositorioViaje viajeRepository) {
        this.viajeRepository=viajeRepository;
    }

    @Override
    public Long guardar(Viaje viaje) {
        return viajeRepository.guardar(viaje);
    }


}
