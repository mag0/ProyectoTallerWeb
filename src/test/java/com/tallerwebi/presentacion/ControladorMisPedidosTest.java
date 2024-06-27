package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.Zona;
import com.tallerwebi.dominio.enums.Estado;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosDisponibles;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorMisPedidosTest {

    RepositorioVehiculo vehiculoRepository;
    RepositorioUsuario usuarioRepository;
    ServicioPedido pedidoService = mock(ServicioPedido.class);
    ServicioSolicitud solicitudService = mock(ServicioSolicitud.class);
    ServicioViaje viajeService = mock(ServicioViaje.class);
    ControladorMisPedidos controladorMisPedidos;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(RepositorioUsuario .class);
        vehiculoRepository = mock(RepositorioVehiculo.class);
        controladorMisPedidos = new ControladorMisPedidos(pedidoService, solicitudService, viajeService);
    }

    @Test
    void noSePuedenAsignarTodosLosPedidosDeLaSolicitud(){
        Long idSolicitud = givenTengoUnaSolicitud();
        List<Double> valores = givenTengoUnaListaDeValores();

        doThrow(NoHayVehiculosDisponibles.class)
                .when(pedidoService)
                .asignarPedidos(new ArrayList<>());

        ModelAndView mav = whenEnvioLosPedidosAlServicio(valores, idSolicitud);

        thenEnviaMensajeDeErrorALaVistaMisPedidos(mav);
    }

    private List<Double> givenTengoUnaListaDeValores() {
        return new ArrayList<>();
    }

    private Long givenTengoUnaSolicitud() {
        return 1L;
    }

    private ModelAndView whenEnvioLosPedidosAlServicio(List<Double> valores, Long idSolicitud) {
        return controladorMisPedidos.asignarPedido(idSolicitud, valores,valores,valores);
    }

    private void thenEnviaMensajeDeErrorALaVistaMisPedidos(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/misPedidos?error=true"));
    }

    @Test
    void seAsignanTodosLosPedidosDeLaSolicitudYMustraLosViajes(){
        Long idSolicitud = givenTengoUnaSolicitud();
        List<Double> valores = givenTengoUnaListaDeValores();

        ModelAndView mav = whenEnvioLosPedidosAlServicio(valores, idSolicitud);

        thenMustraLosViajesEnLaVistaViajes(mav);
    }

    private void thenMustraLosViajesEnLaVistaViajes(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("viajes"));
    }
}
