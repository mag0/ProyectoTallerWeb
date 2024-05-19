package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.presentacion.requests.AsignarPedidoRequest;
import com.tallerwebi.presentacion.requests.DatosLogin;
import com.tallerwebi.servicios.ServicioLogin;
import com.tallerwebi.servicios.ServicioPedido;
import com.tallerwebi.servicios.ServicioVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ControladorPedidosTest {
    private ControladorPedidos pedidosController;
    private Pedido pedidoMock;
    private Vehiculo vehiculoMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioPedido pedidoServiceMock;
    private ServicioVehiculo vehiculoServiceMock;
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
        pedidosController = new ControladorPedidos(pedidoServiceMock,vehiculoServiceMock);

    }

    @Test
    public void agregarUnPedidoAUnVehiculoQueGeneraUnViaje() throws Exception {
        // preparacion
        Integer vehiculoId = 1;
        Long pedidoId = 1L;

        when(vehiculoServiceMock.buscarVehiculo(vehiculoId)).thenReturn(vehiculoMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(pedidoServiceMock.agregarPedido(vehiculoMock, pedidoId)).thenReturn(1L);

        // ejecucion
        //ResponseEntity<Map<String, Object>> modelAndView = pedidosController.asignarPedido(pedidoId,asignarPedidoRequest);


    }
}
