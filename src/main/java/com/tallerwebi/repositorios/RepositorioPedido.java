package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Pedido;

import java.util.List;

public interface RepositorioPedido {
    Pedido buscarPedido(Long id);
    Long guardar(Pedido pedido);
    void modificar(Pedido pedido);
    List<Pedido> buscarTodos();
    List<Pedido> buscarSinViajes();
    boolean eliminarPedido(Long id);
    List<Pedido> obtenerTodosLosPedidos();

    List<Pedido> getPedidosByIds(List<Long> pedidoIds);

    void guardarTodos(List<Pedido> pedidos);

    List<Pedido> obtenerPedidosPorSolicitud(Long idSolicitud);
}
