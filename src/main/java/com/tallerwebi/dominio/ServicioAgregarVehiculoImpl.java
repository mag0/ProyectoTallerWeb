package com.tallerwebi.dominio;

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
            throw new DatosIncompletos("Faltan datos");
        }
        if (flotaDeVehiculos.contieneVehiculo(vehiculo)) {
            throw new VehiculoExistente("El vehiculo ya existe");
        }
        flotaDeVehiculos.agregarVehiculo(vehiculo);
    }

    private Boolean verificarQueSeRecibioTodosLosDatos(Vehiculo vehiculo) {
        return vehiculo.getCombustible() == null || vehiculo.getCapacidad() == null ||
                vehiculo.getResistencia() == null || vehiculo.getMarca() == null ||
                vehiculo.getKilometrajeMaximo() == null ||
                vehiculo.getModelo() == null;
    }
}
