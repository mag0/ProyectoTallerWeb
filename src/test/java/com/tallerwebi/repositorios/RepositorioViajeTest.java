package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes={SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioViajeTest {
/*
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioViaje repositorioViaje;

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerLosViajesDeUnVehiculo() {

        givenExisteUnViaje("pdo557");
        givenExisteUnViaje("pdo557");
        givenExisteUnViaje("abc123");
        List<Viaje> viajes = whenBuscoViajePorVehiculo("abc123");
        thenEncuentroLosViajes(viajes);

    }

    private void thenEncuentroLosViajes(List<Viaje> viajes) {

        assertThat(viajes.size(),equalTo(2));
    }

    private List<Viaje> whenBuscoViajePorVehiculo(String patente) {

        return repositorioViaje.buscarPorVehiculo(patente);
    }

    private void givenExisteUnViaje(String patente) {
        Viaje nuevoViaje = new Viaje();
        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setPatente(patente);
        nuevoViaje.setVehiculo(nuevoVehiculo);
        sessionFactory.getCurrentSession().save(nuevoViaje);
    }*/
}
