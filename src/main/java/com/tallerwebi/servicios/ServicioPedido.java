package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;

import java.time.LocalDate;
import java.util.List;

public interface ServicioPedido {
    Long agregarPedido(Vehiculo vehiculo, Long pedidoId) throws Exception;
    Pedido getPedido(Long id);
    boolean eliminarPedido(Long id);
    List<Pedido> obtenerTodosLosPedidos();
    Pedido buscarPorId(Long id);
    void reprogramarFecha(Long id, LocalDate nuevaFecha);
}
