package com.tallerwebi.dominio;

import com.tallerwebi.servicios.ServicioAgregarVehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioAgregarVehiculoImpl implements ServicioAgregarVehiculo {

    @Override
    public void agregarVehiculo(Vehiculo vehiculo, FlotaDeVehiculos flotaDeVehiculos) {

        if(verificarQueSeRecibioTodosLosDatos(vehiculo)){
            throw new DatosIncompletos();
        }
        if (flotaDeVehiculos.contieneVehiculo(vehiculo)) {
            throw new VehiculoExistente();
        }
        flotaDeVehiculos.agregarVehiculo(vehiculo);
    }

    private Boolean verificarQueSeRecibioTodosLosDatos(Vehiculo vehiculo) {
        return vehiculo.getCombustible() == 0 || vehiculo.getCapacidad() == 0 ||
                vehiculo.getResistencia() == 0 || vehiculo.getMarca().isEmpty() ||
                vehiculo.getKilometrajeMaximo() == 0 || vehiculo.getModelo().isEmpty()
                ||vehiculo.getTipo().isEmpty();
    }
}
