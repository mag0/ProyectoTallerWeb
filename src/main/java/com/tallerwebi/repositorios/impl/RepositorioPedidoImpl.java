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
    public Pedido buscarPedido(Long id) {
        return sessionFactory.getCurrentSession().get(Pedido.class, id);
    }

    @Override
    public void guardar(Pedido pedido) {
        sessionFactory.getCurrentSession().save(pedido);
    }

    @Override
    public void modificar(Pedido pedido) {
        sessionFactory.getCurrentSession().update(pedido);
    }
}
