package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;

public interface ServicioPedido {
    Long agregarPedido(Vehiculo vehiculo, Long pedidoId) throws Exception;
    Pedido getPedido(Long id);

}
