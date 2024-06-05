package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioModificarVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioModificarVehiculoImpl implements ServicioModificarVehiculo {

    RepositorioVehiculo repositorioVehiculo;
    RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioModificarVehiculoImpl(RepositorioVehiculo repositorioVehiculo, RepositorioUsuario repositorioUsuario) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void modificarVehiculo(Vehiculo vehiculo) {
        if(verificarQueSeRecibioTodosLosDatos(vehiculo)){
            throw new DatosIncompletos();
        }
        Vehiculo vehiculoBuscado = repositorioVehiculo.buscar(vehiculo.getId());
        vehiculoBuscado.setModelo(vehiculo.getModelo());
        vehiculoBuscado.setMarca(vehiculo.getMarca());
        vehiculoBuscado.setCombustible(vehiculo.getCombustible());
        vehiculoBuscado.setTipo(vehiculo.getTipo());
        vehiculoBuscado.setCapacidad(vehiculo.getCapacidad());
        vehiculoBuscado.setKilometrajeMaximo(vehiculo.getKilometrajeMaximo());
        vehiculoBuscado.setPatente(vehiculo.getPatente());
        vehiculoBuscado.setResistencia(vehiculo.getResistencia());
        repositorioVehiculo.actualizar(vehiculoBuscado);
    }

    private Boolean verificarQueSeRecibioTodosLosDatos(Vehiculo vehiculo) {
        return vehiculo.getCombustible() == 0 || vehiculo.getCapacidad() == 0 ||
                vehiculo.getResistencia() == 0 || vehiculo.getMarca().isEmpty() ||
                vehiculo.getKilometrajeMaximo() == 0 || vehiculo.getModelo().isEmpty()
                ||vehiculo.getTipo().isEmpty() || vehiculo.getPatente().isEmpty();
    }
}
