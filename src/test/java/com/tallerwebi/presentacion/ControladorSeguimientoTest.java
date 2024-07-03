package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Solicitud;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.Estado;
import com.tallerwebi.servicios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class ControladorSeguimientoTest {
    private MockMvc mockMvc;

    @Mock
    private ServicioSolicitud servicioSolicitud;

    @Mock
    private ServicioInicioDeSesion servicioInicioDeSesion;

    @Mock
    private ServicioPedido servicioPedido;

    @InjectMocks
    private ControladorSeguimiento seguimientoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seguimientoController).build();
    }

    @Test
    public void testFinalizarViajeMedianteSolicitudYPedido() throws Exception {

        Pedido pedido1 = new Pedido();
        pedido1.setEstado(Estado.DISPONIBLE);
        Pedido pedido2 = new Pedido();
        pedido2.setEstado(Estado.DISPONIBLE);

        List<Pedido> pedidos = new ArrayList<>(Arrays.asList(pedido1, pedido2));

        Usuario usuario = new Usuario();
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setPedidos(pedidos);
        solicitud.setUsuario(usuario);

        // Configurar los mocks
        when(servicioInicioDeSesion.buscarUsuarioActivo()).thenReturn(usuario);
        when(servicioSolicitud.getSolicitudById(1L)).thenReturn(solicitud);

        // Ejecutar la solicitud y verificar el resultado
        mockMvc.perform(
                post("/seguimiento/"+solicitud.getId()+"/finalizar/"+solicitud.getPedidos().get(0).getId()))
                .andExpect(view().name("finalizarSeguimiento"));

        // Verificar que se llamaron a los métodos esperados
        verify(servicioSolicitud, times(1)).getSolicitudById(any(Long.class));
        verify(servicioPedido, times(1)).entregarPedidoDeUnaSolicitud(any(Pedido.class));
    }
}
