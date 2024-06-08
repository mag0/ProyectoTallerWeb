package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import com.tallerwebi.servicios.ServicioViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;


public class ControladorPedidosTest {
    private ControladorPedidos pedidosController;
    private Pedido pedidoMock;
    private Vehiculo vehiculoMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioPedido pedidoServiceMock;
    private ServicioVehiculo vehiculoServiceMock;
    private ServicioViaje viajeServiceMock;
    private ServicioMostrarVehiculos servicioMostrarVehiculos;
    private AsignarPedidoRequest asignarPedidoRequest;

    @BeforeEach
    public void init(){
        asignarPedidoRequest = new AsignarPedidoRequest();
        pedidoMock = mock(Pedido.class);
        vehiculoMock = mock(Vehiculo.class);
       // when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        pedidoServiceMock = mock(ServicioPedido.class);
        vehiculoServiceMock = mock(ServicioVehiculo.class);
        servicioMostrarVehiculos = mock(ServicioMostrarVehiculos.class);
        viajeServiceMock = mock(ServicioViaje.class);
        pedidosController = new ControladorPedidos(pedidoServiceMock,servicioMostrarVehiculos,viajeServiceMock, vehiculoServiceMock);

    }

    @Test
    public void agregarUnPedidoAUnVehiculoQueGeneraUnViaje() throws Exception {
        // preparacion
        Long vehiculoId = 1L;
        Long pedidoId = 1L;

        when(vehiculoServiceMock.buscarVehiculo(vehiculoId)).thenReturn(vehiculoMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(pedidoServiceMock.agregarPedido(vehiculoMock, pedidoId)).thenReturn(1L);

        // ejecucion
        //ResponseEntity<Map<String, Object>> modelAndView = pedidosController.asignarPedido(pedidoId,asignarPedidoRequest);


    }

}
