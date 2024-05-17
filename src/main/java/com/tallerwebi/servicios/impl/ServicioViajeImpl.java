package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.Zona;
import com.tallerwebi.servicios.ServicioViaje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private Vehiculo vehiculo;
    private Zona zona;


    public ServicioViajeImpl(Vehiculo vehiculo, Zona zona) {
        this.vehiculo = vehiculo;
        this.zona = zona;
    }

    @Override
    public Viaje crearViaje(Long vehiculoId, Long zonaId, List<Long> paqueteIds) {

      /*  Vehiculo vehiculo = vehiculo.findById(vehiculoId);
        //Zona zona = this.zona.buscarPorId(zonaId);


        if (vehiculo == null || zona == null) {
            return null;
        }


        double consumoNafta = calcularConsumoNafta(vehiculo, zona);


        Viaje viaje = new Viaje(vehiculoId, zonaId, paqueteIds);
        viaje.setConsumoNafta(consumoNafta);
        return viaje;



    }

    private double calcularConsumoNafta(Vehiculo vehiculo, Zona zona) {

        double distancia = zona.getDistanciaDelDeposito();
        double consumoNaftaPorKm = vehiculo.getCombustible(); // Supongamos que esto es el consumo por kilómetro
        return distancia * consumoNaftaPorKm;
   */  return null;
    }


    }
