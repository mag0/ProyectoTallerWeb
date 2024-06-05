package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;

import java.util.List;

import java.time.LocalDate;

public interface ServicioPedido {
    Long agregarPedido(Vehiculo vehiculo, Long pedidoId) throws Exception;
    Pedido getPedido(Long id);

    List<Pedido> getAll();

    List<Pedido> getAllPedidosSinViaje();
    boolean eliminarPedido(Long id);
    List<Pedido> obtenerTodosLosPedidos();
    Pedido buscarPorId(Long id);
    void reprogramarFecha(Long id, LocalDate nuevaFecha);
    void actualizarPedido(Pedido pedido);
}
