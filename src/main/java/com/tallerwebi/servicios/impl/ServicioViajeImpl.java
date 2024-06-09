package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje viajeRepository;

    @Autowired
    public ServicioViajeImpl(RepositorioViaje viajeRepository) {
           this.viajeRepository=viajeRepository;
    }

    @Override
    public Long guardar(Viaje viaje) {
        return viajeRepository.guardar(viaje);
    }

    @Override
    public List<Viaje> buscarTodos() {

        return viajeRepository.getAll();
    }

    @Override
    public Map<String, Long> obtenerViajesPorVehiculo() {
        return viajeRepository.contarViajesPorVehiculo();
    }


}
