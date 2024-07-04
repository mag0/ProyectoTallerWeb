package com.tallerwebi.servicios;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.Estado;
import com.tallerwebi.repositorios.RepositorioPedido;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.repositorios.RepositorioViaje;
import com.tallerwebi.servicios.impl.ServicioMostrarVehiculosImpl;
import com.tallerwebi.servicios.impl.ServicioPedidoImpl;
import com.tallerwebi.servicios.impl.ServicioSolicitudImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServicioPedidoTest {

    private RepositorioPedido pedidoRepository;
    private RepositorioVehiculo vehiculoRepository;
    private RepositorioUsuario usuarioRepository;
    private RepositorioViaje viajeRepository;
    private ServicioPedido pedidoService;

    @BeforeEach
    void setUp() {
        pedidoRepository = mock(RepositorioPedido.class);
        vehiculoRepository = mock(RepositorioVehiculo.class);
        usuarioRepository = mock(RepositorioUsuario.class);
        viajeRepository = mock(RepositorioViaje.class);
        ServicioSolicitud servicioSolicitud = mock(ServicioSolicitud.class);
        pedidoService = new ServicioPedidoImpl(pedidoRepository, viajeRepository, vehiculoRepository, usuarioRepository, (ServicioSolicitudImpl) servicioSolicitud);
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
    void testGetPedidosByIds() throws Exception {
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

    @Test
    void seAsignaUnaSolicitudADiferentesViajesHabiendoTrafico() {
        // Arrange
        List<Pedido> pedidos = givenTengoUnaListaDePedidos();
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos();
        Usuario usuario = new Usuario();

        // Act
        when(usuarioRepository.buscarUsuarioActivo()).thenReturn(usuario);
        when(vehiculoRepository.buscarTodosLosDisponiblesPorUsuario(usuario.getId())).thenReturn(vehiculos);

        List<Viaje> viajes = whenCreoLosViajesNecesarios(pedidos);

        // Assert

        thenTengoLosViajesCreados(viajes, pedidos, vehiculos);
    }

    private List<Vehiculo> givenTengoUnaListaDeVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        Vehiculo vehiculo = new Vehiculo("ACM1PT","Ford","Fiesta","Auto", 1994, 123,123,20,true);
        Vehiculo vehiculo2 = new Vehiculo("ACM1P","Ford","Fiesta","Moto", 1994, 123,123,20,true);
        vehiculos.add(vehiculo);
        vehiculos.add(vehiculo2);
        return vehiculos;
    }

    private List<Pedido> givenTengoUnaListaDePedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        Zona zona = new Zona(1L, 12, 35);
        Zona zona2 = new Zona(2L, 17, 34);
        Pedido pedido = new Pedido("Paquete 1","Electronica","DS225",10,10, LocalDate.now(), Estado.DISPONIBLE, zona);
        Pedido pedido2 = new Pedido("Paquete 2","Electronica","DS224",10,10, LocalDate.now(), Estado.DISPONIBLE, zona);
        Pedido pedido3 = new Pedido("Paquete 3","Electronica","DS226",10,10, LocalDate.now(), Estado.DISPONIBLE, zona2);
        pedido.setDistancia(12.0);pedido.setDistanciaConTrafico(16.0);pedido.setTiempoConTrafico(45.0);
        pedido2.setDistancia(12.0);pedido2.setDistanciaConTrafico(16.0);pedido2.setTiempoConTrafico(45.0);
        pedido3.setDistancia(12.0);pedido3.setDistanciaConTrafico(16.0);pedido3.setTiempoConTrafico(45.0);
        pedidos.add(pedido);
        pedidos.add(pedido2);
        pedidos.add(pedido3);
        return pedidos;
    }

    private List<Viaje> whenCreoLosViajesNecesarios(List<Pedido> pedidos) {
        return pedidoService.asignarPedidos(pedidos);
    }

    private void thenTengoLosViajesCreados(List<Viaje> viajes, List<Pedido> pedidos, List<Vehiculo> vehiculos) {
        for (Viaje viaje : viajes) {
            verify(viajeRepository, times(1)).guardar(viaje);
        }
        for (Pedido pedido : pedidos) {
            verify(pedidoRepository, times(1)).modificar(pedido);
        }
        verify(vehiculoRepository, times(1)).actualizar(vehiculos.get(0));
        verify(vehiculoRepository, times(2)).actualizar(vehiculos.get(1));
        assertEquals(viajes.size(), 2);
        assertEquals(viajes.get(0).getVehiculo().getTipo(), "Moto");

    }

    @Test
    void seAsignaUnaSolicitudADiferentesViajesSinHaberTrafico() {
        // Arrange
        List<Pedido> pedidos = givenTengoUnaListaDePedidos();
        for (Pedido pedido : pedidos) {
            pedido.setDistancia(12.0);pedido.setDistanciaConTrafico(10.0);pedido.setTiempoConTrafico(45.0);
        }
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos();
        Usuario usuario = new Usuario();

        // Act
        when(usuarioRepository.buscarUsuarioActivo()).thenReturn(usuario);
        when(vehiculoRepository.buscarTodosLosDisponiblesPorUsuario(usuario.getId())).thenReturn(vehiculos);

        List<Viaje> viajes = whenCreoLosViajesNecesarios(pedidos);

        // Assert
        thenTengoLosViajesCreadosSinHaberDemora(viajes, pedidos, vehiculos);
    }

    private void thenTengoLosViajesCreadosSinHaberDemora(List<Viaje> viajes, List<Pedido> pedidos, List<Vehiculo> vehiculos) {
        for (Viaje viaje : viajes) {
            verify(viajeRepository, times(1)).guardar(viaje);
        }
        for (Pedido pedido : pedidos) {
            verify(pedidoRepository, times(1)).modificar(pedido);
        }
        verify(vehiculoRepository, times(2)).actualizar(vehiculos.get(0));
        verify(vehiculoRepository, times(1)).actualizar(vehiculos.get(1));
        assertEquals(viajes.size(), 2);
        assertEquals(viajes.get(0).getVehiculo().getTipo(), "Auto");
    }

}
