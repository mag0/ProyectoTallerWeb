package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.servicios.impl.ServicioPedidoImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;


public class ServicioPedidoTest {

    @Test
    public void seAgregaUnPedidoAUnVehiculo() throws Exception {
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Fusca", 1994, 123,123,123);
        Pedido pedido = new Pedido("pedido1","asd","asd",123,123);

        ServicioPedido servicio = new ServicioPedidoImpl();

        try {
            Viaje result = servicio.agregarPedido(vehiculo, pedido);

            assertThat(result, notNullValue());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void noSePuedeAgregarUnPedidoAUnVehiculoLleno(){
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Fusca", 1994, 123,123,10);
        Pedido pedido = new Pedido("pedido1","asd","asd",123,123);

        ServicioPedido servicio = new ServicioPedidoImpl();

        assertThrows(Exception.class, () -> servicio.agregarPedido(vehiculo, pedido));

    }
}
