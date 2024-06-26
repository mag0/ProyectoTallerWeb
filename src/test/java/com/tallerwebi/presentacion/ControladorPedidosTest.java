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
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class ControladorPedidosTest {
    private MockMvc mockMvc;

    @Mock
    private ServicioPedido pedidoService;

    @Mock
    private ServicioSolicitud servicioSolicitud;

    @Mock
    private ServicioInicioDeSesion servicioInicioDeSesion;

    @InjectMocks
    private ControladorPedidos solicitudController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(solicitudController).build();
    }

    @Test
    public void testConfirmarSolicitud() throws Exception {
        // Preparar datos de prueba
        List<Long> pedidoIds = Arrays.asList(1L, 2L);
        Pedido pedido1 = new Pedido();
        pedido1.setEstado(Estado.DISPONIBLE);
        Pedido pedido2 = new Pedido();
        pedido2.setEstado(Estado.DISPONIBLE);
        Usuario usuario = new Usuario();
        // Configurar los mocks
        when(servicioInicioDeSesion.buscarUsuarioActivo()).thenReturn(usuario);
        when(pedidoService.getPedidosByIds(pedidoIds)).thenReturn(Arrays.asList(pedido1, pedido2));

        // Ejecutar la solicitud y verificar el resultado
        mockMvc.perform(post("/ofertas/confirmarSolicitud")
                        .param("pedidoIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ofertas"));

        // Verificar que se llamaron a los métodos esperados
        verify(servicioSolicitud, times(1)).guardar(any(Solicitud.class));
        verify(pedidoService, times(1)).guardarTodos(anyList());
    }

    @Test
    public void testConfirmarSolicitudConError() throws Exception {
        // Preparar datos de prueba
        List<Long> pedidoIds = Arrays.asList(1L, 2L);
        Pedido pedido1 = new Pedido();
        pedido1.setEstado(Estado.SOLICITADO);
        Pedido pedido2 = new Pedido();
        pedido2.setEstado(Estado.DISPONIBLE);
        // Configurar los mocks
        when(pedidoService.getPedidosByIds(pedidoIds)).thenReturn(Arrays.asList(pedido1, pedido2));

        // Ejecutar la solicitud y verificar el resultado
        mockMvc.perform(post("/ofertas/confirmarSolicitud")
                        .param("pedidoIds", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
        // Verificar que no se llamaron a los métodos de guardar
        verify(servicioSolicitud, never()).guardar(any(Solicitud.class));
        verify(pedidoService, never()).guardarTodos(anyList());
    }
}
