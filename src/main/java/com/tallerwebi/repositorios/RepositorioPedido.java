package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Pedido;

import java.util.List;

public interface RepositorioPedido {
    Pedido buscarPedido(Long id);
    void guardar(Pedido pedido);
    void modificar(Pedido pedido);
    boolean eliminarPedido(Long id);
    List<Pedido> obtenerTodosLosPedidos();
}
