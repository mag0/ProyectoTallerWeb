package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioPedido;
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

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido pedidoRepository, RepositorioViaje viajeRepository) {
        this.pedidoRepository = pedidoRepository;
        this.viajeRepository = viajeRepository;
    }

    @Override
    public Long agregarPedido(Vehiculo vehiculo, int pedidoId) throws Exception {
        List<Pedido> pedidosList = new ArrayList<>();
        Pedido pedido = this.getPedido(pedidoId);

        try {

            if(vehiculo.getCapacidad() < pedido.getPeso()){
                throw new Exception("El vehiculo no tiene la capacidad para cargar el pedido");
            }

            pedidosList.add(pedido);
            Viaje viaje = new Viaje(1L,vehiculo,pedidosList);

            Long viajeId = viajeRepository.guardar(viaje);
            return viajeId;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido getPedido(int id) {
        return pedidoRepository.buscarPedido(id);
    }
}
