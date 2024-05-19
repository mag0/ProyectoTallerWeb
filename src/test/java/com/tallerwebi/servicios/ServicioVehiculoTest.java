package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ServicioVehiculoTest {

    @Test
    public void seCargaUnPedidoAUnVehiculo() throws Exception {
        List<Pedido> pedidoList = new ArrayList<>();
        Pedido pedido = new Pedido();
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Fusca", 1994, 123,123,10,true);
        Viaje viaje = new Viaje(1L,vehiculo,pedidoList);

        pedidoList.add(pedido);

        ServicioVehiculo servicioV = Mockito.mock(ServicioVehiculo.class);

        Mockito.when(servicioV.cargarUnPaquete(vehiculo,pedido)).thenReturn(viaje);

        Viaje viajeEsperado = servicioV.cargarUnPaquete(vehiculo,pedido);

        assertThat(viajeEsperado, notNullValue());
    }
}
