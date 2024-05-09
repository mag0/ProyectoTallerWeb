package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedidos;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;

public interface ServicioPedido {
    Viaje agregarPedido(Vehiculo vehiculo, Pedidos pedidos) throws Exception;
}
