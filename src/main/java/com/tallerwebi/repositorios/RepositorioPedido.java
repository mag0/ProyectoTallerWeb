package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Pedido;

public interface RepositorioPedido {
    Pedido buscarPedido(Long id);
    void guardar(Pedido pedido);
    void modificar(Pedido pedido);
}
