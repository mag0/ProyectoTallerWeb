package com.tallerwebi.repositorios;

import com.tallerwebi.dominio.*;

import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import com.tallerwebi.servicios.ServicioViaje;
import com.tallerwebi.servicios.impl.ServicioPedidoImpl;
import com.tallerwebi.servicios.impl.ServicioVehiculoImpl;
import com.tallerwebi.servicios.impl.ServicioViajeImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;


public class RepositorioRendimientoTest {

/*

    RepositorioVehiculo repositorioVehiculo = mock(RepositorioVehiculo.class);
    ServicioVehiculo servicioVehiculo = new ServicioVehiculoImpl(repositorioVehiculo);

    RepositorioPedido repositorioPedido = mock(RepositorioPedido.class);
    ServicioPedido servicioPedido = new ServicioPedidoImpl(repositorioPedido);

    RepositorioViaje repositorioViaje = mock(RepositorioViaje.class);
    ServicioViaje servicioViaje = new ServicioViajeImpl(repositorioViaje);





    @Test
    @Transactional
    @Rollback
    public void puedoObtenerLosViajesDeUnVehiculo(){@Test
    @Transactional
    @Rollback
    public void puedoObtenerLosViajesDeUnVehiculo(){

       Vehiculo vehiculo = new Vehiculo( "abc123","renault","clio","auto",1000,100,100,100,true);
       givenExisteUnPedido();
        Zona zona1 =givenExisteUnaZona();

        Viaje viaje1 = givenExisteUnViaje(zona1,auto1,pedido1);

        whenBuscoVehiculoPorPatenteEnLosViaje();
        thenObtengoListaDeViajePorPatente();

    }

    private Pedido givenExisteUnPedido(String nombre, String tipo, String codigo, Integer tamanio, Integer peso, LocalDate fecha) {
       Pedido nuevoPedido = new Pedido(nombre, )
    }

    private Vehiculo givenExisteUnVehiculo(String patente, String marca, String modelo, String tipo, int kilometrajeMaximo,int resistencia, int combustible, int capacidad, boolean status) {
        repositorioVehiculo.guardar();
        return servicioVehiculo.(patente, marca,  modelo,  tipo,  kilometrajeMaximo,  combustible,  resistencia,  capacidad,  status);
        }




    private void givenExisteUnViaje(Zona zona, Vehiculo vehiculo, Pedido pedido) {
        return servicioViaje.(zona,vehiculo,pedido);


    }


    List<Usuario> usuariosBuscados = whenBuscoUsuariosPorRol ("admin");
    thenObtengoUsuarios (usuarios Buscados);
    private void thenbtengoUsuarios (List<Usuario> usuariosBuscados) { assertThat(usuariosBuscados.size(), equalTo( operand: 2)); I
    }

    private List<Usuario> whenBuscoUsuarios PorRol (String rol) { return repositorioUsuario.buscar PorRol (rol);

}*/
}
