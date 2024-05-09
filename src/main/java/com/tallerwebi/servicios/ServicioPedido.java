package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;

public interface ServicioPedido {
    Viaje agregarPedido(Vehiculo vehiculo, Pedido pedidos) throws Exception;
}
