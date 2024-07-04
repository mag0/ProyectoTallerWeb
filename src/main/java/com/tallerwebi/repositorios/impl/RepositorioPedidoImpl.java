package com.tallerwebi.repositorios.impl;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.repositorios.RepositorioPedido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Long guardar(Pedido pedido) {
        return (Long) sessionFactory.getCurrentSession().save(pedido);
    }

    @Override
    public void modificar(Pedido pedido) {
        sessionFactory.getCurrentSession().update(pedido);
    }

    @Override
    public boolean eliminarPedido(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Pedido pedido = session.get(Pedido.class, id);
        if (pedido != null) {
            session.delete(pedido);
            return true;
        }
        return false;
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() {
        Session session = sessionFactory.getCurrentSession();
        Query<Pedido> query = session.createQuery("FROM Pedido", Pedido.class);
        return query.getResultList();
    }

    @Override
    public List<Pedido> getPedidosByIds(List<Long> pedidoIds) {
        Session session = sessionFactory.getCurrentSession();
        // Crea una consulta HQL para seleccionar pedidos por sus IDs
        Query<Pedido> query = session.createQuery("FROM Pedido WHERE id IN (:ids)", Pedido.class);
        // Establece los parámetros de la consulta
        query.setParameterList("ids", pedidoIds);
        // Ejecuta la consulta y retorna la lista de resultados
        return query.list();
    }

    @Override
    public void guardarTodos(List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            sessionFactory.getCurrentSession().merge(pedido);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorSolicitud(Long idSolicitud) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Pedido WHERE solicitud.id = :idSolicitud", Pedido.class)
                .setParameter("idSolicitud", idSolicitud)
                .getResultList();
    }


    @Override
    public List<Pedido> buscarTodos() {
        return sessionFactory.getCurrentSession().createQuery("from Pedido").list();
    }

    @Override
    public List<Pedido> buscarSinViajes() {
        return sessionFactory.getCurrentSession().createQuery("from Pedido where viaje is null").list();
    }
}
