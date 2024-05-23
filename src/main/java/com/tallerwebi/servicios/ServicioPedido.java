package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;

import java.util.List;

public interface ServicioPedido {
    Long agregarPedido(Vehiculo vehiculo, Long pedidoId) throws Exception;
    Pedido getPedido(Long id);

    List<Pedido> getAll();

    List<Pedido> getAllPedidosSinViaje();
}
