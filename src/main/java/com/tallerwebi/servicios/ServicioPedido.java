package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;

public interface ServicioPedido {
    Long agregarPedido(Vehiculo vehiculo, int pedidoId) throws Exception;
    Pedido getPedido(int id);

}
