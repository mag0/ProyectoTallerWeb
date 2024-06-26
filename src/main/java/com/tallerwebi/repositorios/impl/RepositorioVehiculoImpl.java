package com.tallerwebi.repositorios.impl;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioVehiculoImpl implements RepositorioVehiculo {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioVehiculoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
    }

    @Override
    public void actualizar(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().update(vehiculo);
    }

    @Override
    public Vehiculo buscar(Long id) {
        return sessionFactory.getCurrentSession().get(Vehiculo.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vehiculo> buscarTodos() {
        return sessionFactory.getCurrentSession().createQuery("from Vehiculo").list();
    }

    @Override
    public List<Vehiculo> buscarTodosPorUsuario(Long idUsuario) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Vehiculo.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .list();
    }

    @Override
    public List<Vehiculo> buscarTodosLosDisponiblesPorUsuario(Long idUsuario) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Vehiculo.class);

        // Agregar restricciones
        criteria.add(Restrictions.eq("usuario.id", idUsuario)); // Usuario específico
        criteria.add(Restrictions.eq("status", true)); // Vehículos con status true

        return criteria.list();
    }

    @Override
    public void eliminar(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().delete(vehiculo);
    }

}

