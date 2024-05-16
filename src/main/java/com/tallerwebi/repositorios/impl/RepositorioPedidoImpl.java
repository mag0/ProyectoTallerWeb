package com.tallerwebi.repositorios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.repositorios.RepositorioPedido;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPedido")
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Pedido buscarPedido(int id) {
        return null;
    }

    @Override
    public void guardar(Pedido pedido) {

    }

    @Override
    public void modificar(Pedido pedido) {

    }
}
