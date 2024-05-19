package com.tallerwebi.repositorios.impl;

import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioViaje;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("repositorioViaje")
public class RepositorioViajeImpl implements RepositorioViaje {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioViajeImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Long guardar(Viaje viaje) {
        return (Long) sessionFactory.getCurrentSession().save(viaje);
    }

    @Override
    public List<Viaje> getAll() {
        return (List<Viaje>) sessionFactory.getCurrentSession().createQuery("from Viaje").list();
    }
}
