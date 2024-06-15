package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioPedido;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.impl.ServicioViajeImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioViajesTest {

    @Test
    public void buscarTodosLosViajes() {
        RepositorioViaje viajeRepository = mock(RepositorioViaje.class);
        RepositorioPedido pedidoRepository = mock(RepositorioPedido.class);
        ServicioViaje viajesService = new ServicioViajeImpl(viajeRepository, pedidoRepository);
        Vehiculo v = mock(Vehiculo.class);
        List<Viaje> viajesEsperados = new ArrayList<>();
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(mock(Pedido.class));
        viajesEsperados.add(new Viaje(1L,v,pedidos, pedidos.get(0).getFecha()));

        when(viajesService.buscarTodos()).thenReturn(viajesEsperados);

        List<Viaje> viajes = viajesService.buscarTodos();

        assertEquals(viajes.size(),1);
    }
}
