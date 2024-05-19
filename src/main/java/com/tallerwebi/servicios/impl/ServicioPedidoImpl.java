package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioPedido;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {
    private RepositorioPedido pedidoRepository;
    private RepositorioViaje viajeRepository;
    private RepositorioVehiculo vehiculoRepository;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido pedidoRepository, RepositorioViaje viajeRepository, RepositorioVehiculo vehiculoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.viajeRepository = viajeRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public Long agregarPedido(Vehiculo vehiculo, Long pedidoId) throws Exception {
        List<Pedido> pedidosList = new ArrayList<>();
        Pedido pedido = this.getPedido(pedidoId);
        try {
            if(vehiculo.getCapacidad() < pedido.getPeso()){
                throw new Exception("El vehiculo no tiene la capacidad para cargar el pedido");
            }

            pedidosList.add(pedido);
            vehiculo.setCapacidad(vehiculo.getCapacidad() - pedido.getPeso());
            vehiculoRepository.actualizar(vehiculo);

            Viaje viaje = new Viaje(1L,vehiculo,pedidosList);

            return viajeRepository.guardar(viaje);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido getPedido(Long id) {
        return pedidoRepository.buscarPedido(id);
    }
}
