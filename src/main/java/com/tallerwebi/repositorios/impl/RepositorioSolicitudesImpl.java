package com.tallerwebi.repositorios.impl;

import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.repositorios.RepositorioSolicitudes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public class RepositorioSolicitudesImpl implements RepositorioSolicitudes {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @Transactional
    public void guardar(Solicitud solicitud) {
        sessionFactory.getCurrentSession().saveOrUpdate(solicitud);
    }

    @Override
    @Transactional
    public List<Solicitud> getAllSolicitudes() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Solicitud", Solicitud.class).getResultList();
    }
}
