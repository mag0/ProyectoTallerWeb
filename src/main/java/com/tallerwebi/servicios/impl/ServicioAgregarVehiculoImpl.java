package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioAgregarVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioAgregarVehiculoImpl implements ServicioAgregarVehiculo {

    private final RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioAgregarVehiculoImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public void agregarVehiculo(Vehiculo vehiculo) {

        if(verificarQueSeRecibioTodosLosDatos(vehiculo)){
            throw new DatosIncompletos();
        }
        if (repositorioVehiculo.buscarTodos().contains(vehiculo)) {
            throw new VehiculoExistente();
        }
        vehiculo.setStatus(true);
        repositorioVehiculo.guardar(vehiculo);
    }

    private Boolean verificarQueSeRecibioTodosLosDatos(Vehiculo vehiculo) {
        return vehiculo.getCombustible() == 0 || vehiculo.getCapacidad() == 0 ||
                /*<vehiculo.getResistencia() == 0 ||*/ vehiculo.getMarca().isEmpty() ||
                vehiculo.getKilometrajeMaximo() == 0 || vehiculo.getModelo().isEmpty()
                ||vehiculo.getTipo().isEmpty() || vehiculo.getPatente().isEmpty();
    }
}
