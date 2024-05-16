package com.tallerwebi.servicios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.servicios.ServicioPedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {
    @Override
    public Viaje agregarPedido(Vehiculo vehiculo, Pedido pedidos) throws Exception {
        List<Pedido> pedidosList = new ArrayList<>();

        if(vehiculo.getCapacidad() < pedidos.getPeso()){
            throw new Exception("El vehiculo no tiene la capacidad para cargar el pedido");
        }

        try {
            pedidosList.add(pedidos);

            Viaje viaje = new Viaje(1L, 1L, pedidosList, vehiculo.getPatente());
            return viaje;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido getPedido(int id) {
        return null;
    }
}
