package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.*;
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
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes={SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioViajeTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioViaje repositorioViaje;

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerLosViajesDeUnVehiculo() {
        Usuario usuario = givenExisteUnUsuario();
        Vehiculo vehiculo = givenExisteUnVehiculo("pdo557", usuario);
        Vehiculo vehiculo2 = givenExisteUnVehiculo("pdo558", usuario);
        givenExisteUnViaje(vehiculo);
        givenExisteUnViaje(vehiculo);
        givenExisteUnViaje(vehiculo);
        givenExisteUnViaje(vehiculo2);

        List<Viaje> viajes = whenBuscoViajePorVehiculo("pdo557");

        thenEncuentroLosViajes(viajes);
    }

    private Usuario givenExisteUnUsuario() {
        Usuario usuario = new Usuario();
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

    private Vehiculo givenExisteUnVehiculo(String patente, Usuario usuario) {
        Vehiculo nuevoVehiculo = new Vehiculo(patente, "otro", "si", "Auto", 1100, 190, 95, 4, true);
        nuevoVehiculo.setUsuarioID(usuario);
        sessionFactory.getCurrentSession().save(nuevoVehiculo);
        return nuevoVehiculo;
    }

    private List<Viaje> whenBuscoViajePorVehiculo(String patente) {
        return repositorioViaje.buscarPorVehiculo(patente);
    }

    private void givenExisteUnViaje(Vehiculo vehiculo) {
        Viaje nuevoViaje = new Viaje();
        List<Pedido> pedidos = new ArrayList<>();
        Pedido pedido = new Pedido();
        pedidos.add(pedido);
        Zona zona = new Zona();
        zona.setId(1l);
        nuevoViaje.setVehiculo(vehiculo);
        nuevoViaje.setZonaId(zona.getId());
        nuevoViaje.setPedidos(pedidos);
        sessionFactory.getCurrentSession().save(pedido);
        sessionFactory.getCurrentSession().save(nuevoViaje);
    }

    private void thenEncuentroLosViajes(List<Viaje> viajes) {
        assertThat(viajes.size(),equalTo(3));
    }
}
