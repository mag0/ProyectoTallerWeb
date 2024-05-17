package com.tallerwebi.repositorios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioVehiculoImpl implements RepositorioVehiculo {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioVehiculoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
    }

    @Override
    public Vehiculo buscar(int id) {
        return sessionFactory.getCurrentSession().get(Vehiculo.class, id);
    }
}
