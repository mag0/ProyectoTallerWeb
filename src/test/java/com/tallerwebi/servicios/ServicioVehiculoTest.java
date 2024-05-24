package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioVehiculoImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ServicioVehiculoTest {

    ServicioVehiculo servicioV = new ServicioVehiculoImpl(mock(RepositorioVehiculo.class));

    /*
    * @Test
    public void seCargaUnPedidoAUnVehiculo() throws Exception {
        List<Pedido> pedidoList = new ArrayList<>();
        Pedido pedido = new Pedido("pedido1","Fragil","ACM!PT",10,5, LocalDate.now());
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Fusca", 1994, 123,123,10,true);
        Viaje viaje = new Viaje(1L,vehiculo,pedidoList);

        pedidoList.add(pedido);

        Mockito.when(servicioV.cargarUnPaquete(vehiculo,pedido)).thenReturn(viaje);

        Viaje viajeEsperado = servicioV.cargarUnPaquete(vehiculo,pedido);

        assertThat(viajeEsperado, notNullValue());
        assertEquals(viajeEsperado.getVehiculo().getPatente(),vehiculo.getPatente());
    }
    * */
}
