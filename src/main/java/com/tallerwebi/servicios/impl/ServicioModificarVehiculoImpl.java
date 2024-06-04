package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioModificarVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioModificarVehiculoImpl implements ServicioModificarVehiculo {

    RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioModificarVehiculoImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

    @Override
    public void modificarVehiculo(Vehiculo vehiculo) {
        if(verificarQueSeRecibioTodosLosDatos(vehiculo)){
            throw new DatosIncompletos();
        }
        Vehiculo vehiculoExistente = repositorioVehiculo.buscar(vehiculo.getId());
        vehiculoExistente.setPatente(vehiculo.getPatente());
        vehiculoExistente.setMarca(vehiculo.getMarca());
        vehiculoExistente.setModelo(vehiculo.getModelo());
        vehiculoExistente.setTipo(vehiculo.getTipo());
        vehiculoExistente.setKilometrajeMaximo(vehiculo.getKilometrajeMaximo());
        vehiculoExistente.setCombustible(vehiculo.getCombustible());
        vehiculoExistente.setResistencia(vehiculo.getResistencia());
        vehiculoExistente.setCapacidad(vehiculo.getCapacidad());
        repositorioVehiculo.merge(vehiculo);
    }

    private Boolean verificarQueSeRecibioTodosLosDatos(Vehiculo vehiculo) {
        return vehiculo.getCombustible() == 0 || vehiculo.getCapacidad() == 0 ||
                vehiculo.getResistencia() == 0 || vehiculo.getMarca().isEmpty() ||
                vehiculo.getKilometrajeMaximo() == 0 || vehiculo.getModelo().isEmpty()
                ||vehiculo.getTipo().isEmpty() || vehiculo.getPatente().isEmpty();
    }
}
