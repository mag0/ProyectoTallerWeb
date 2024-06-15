package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.excepcion.VehiculoNoTieneCapacidad;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioVehiculoImpl implements ServicioVehiculo {

    private final RepositorioVehiculo vehiculoRepository;

    @Autowired
    public ServicioVehiculoImpl(RepositorioVehiculo vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public Vehiculo buscarVehiculo(Long id) throws Exception {
        return vehiculoRepository.buscar(id);
    }

    @Override
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.buscarTodos();
    }

    @Override
    public Viaje cargarUnPaquete(Vehiculo vehiculo, Pedido pedido) throws VehiculoNoTieneCapacidad {
        List<Pedido> pedidosList = new ArrayList<>();
        pedidosList.add(pedido);
        Viaje viaje = new Viaje();

        if(vehiculo.getCapacidad() < pedido.getPeso()){
            throw new VehiculoNoTieneCapacidad("El vehiculo no tiene la capacidad para cargar el pedido");
        }

        pedido.setViaje(viaje);

        vehiculo.setCapacidad(vehiculo.getCapacidad() - pedido.getPeso());
        vehiculoRepository.actualizar(vehiculo);

        viaje.setVehiculo(vehiculo);
        viaje.setPedidos(pedidosList);
        viaje.setFecha(pedidosList.get(0).getFecha());

        return viaje;
    }
}
