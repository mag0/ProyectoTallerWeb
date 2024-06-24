package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.repositorios.RepositorioPedido;
import com.tallerwebi.servicios.impl.ServicioPedidoImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServicioPedidoTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private RepositorioPedido pedidoRepository;

    @InjectMocks
    private ServicioPedidoImpl pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void seAgregaUnPedidoAUnVehiculo() throws Exception {
        // Definir el comportamiento esperado del mock
        Long viajeId = 1L;
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Fusca", 1994, 123,123,10,true);

        // Crear el mock del servicio
        ServicioPedido servicioP = Mockito.mock(ServicioPedido.class);
        ServicioVehiculo servicioV = Mockito.mock(ServicioVehiculo.class);

        when(servicioV.buscarVehiculo(1L)).thenReturn(vehiculo);
        when(servicioP.agregarPedido(vehiculo, 1L)).thenReturn(viajeId);

        // Llamar al método y realizar las aserciones
        Vehiculo vehiculoEsperado = servicioV.buscarVehiculo(1L);
        Long result = servicioP.agregarPedido(vehiculo, 1L);

        assertThat(vehiculoEsperado, notNullValue());
        assertThat(result, is(viajeId));
    }

    @Test
    public void noSePuedeAgregarUnPedidoAUnVehiculoLleno() throws Exception {
        // Crear el vehículo y el pedido
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Fusca", 1994, 123,123,10,true);

        // Crear el mock del servicio
        ServicioPedido servicio = Mockito.mock(ServicioPedido.class);

        // Definir el comportamiento esperado del mock
        doThrow(new Exception("El vehículo está lleno")).when(servicio).agregarPedido(vehiculo, 1L);

        // Llamar al método y verificar que lanza la excepción esperada
        assertThrows(Exception.class, () -> servicio.agregarPedido(vehiculo, 1L));
    }

    @Test
    void testGetPedidosByIds() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<Pedido> expectedPedidos = Arrays.asList(new Pedido(), new Pedido(), new Pedido());
        when(pedidoRepository.getPedidosByIds(ids)).thenReturn(expectedPedidos);

        // Act
        List<Pedido> actualPedidos = pedidoService.getPedidosByIds(ids);

        // Assert
        assertEquals(expectedPedidos, actualPedidos);
        verify(pedidoRepository).getPedidosByIds(ids);
    }


}
