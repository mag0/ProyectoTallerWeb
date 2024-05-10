package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioMostrarVehiculosImpl implements ServicioMostrarVehiculos {

    @Override
    public List<Vehiculo> mostrarFlota(FlotaDeVehiculos flotaDeVehiculos) {
        inicializarFlota(flotaDeVehiculos);
        if(flotaDeVehiculos.getVehiculos().isEmpty()){
            throw new NoHayVehiculosEnLaFlota();
        }
        return flotaDeVehiculos.getVehiculos();
    }

    private void inicializarFlota(FlotaDeVehiculos flotaDeVehiculos) {
        if (flotaDeVehiculos.getVehiculos().isEmpty()) {

            Vehiculo vehiculo1 = new Vehiculo("ABC123", "Honda", "CBR600RR", "Moto", 15000, 10, 200, 1);
            Vehiculo vehiculo2 = new Vehiculo("DEF456", "Toyota", "Corolla", "Auto", 80000, 50, 300, 5);
            Vehiculo vehiculo3 = new Vehiculo("GHI789", "Volvo", "FH16", "Camión", 500000, 400, 500, 3);
            Vehiculo vehiculo4 = new Vehiculo("JKL012", "Yamaha", "MT-07", "Moto", 20000, 15, 180, 1);

            flotaDeVehiculos.agregarVehiculo(vehiculo1);
            flotaDeVehiculos.agregarVehiculo(vehiculo2);
            flotaDeVehiculos.agregarVehiculo(vehiculo3);
            flotaDeVehiculos.agregarVehiculo(vehiculo4);
        }
    }
}
