package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioMostrarVehiculosImpl implements ServicioMostrarVehiculos {

    FlotaDeVehiculos flotaDeVehiculos;

    public ServicioMostrarVehiculosImpl() {
        this.flotaDeVehiculos = new FlotaDeVehiculos();
    }

    @Override
    public List<Vehiculo> mostrarFlota() {
        if(flotaDeVehiculos.getVehiculos().isEmpty()){
            throw new NoHayVehiculosEnLaFlota();
        }
        return flotaDeVehiculos.getVehiculos();
    }
}
