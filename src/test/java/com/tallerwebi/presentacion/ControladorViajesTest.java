package com.tallerwebi.presentacion;

import com.tallerwebi.servicios.ServicioViaje;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorViajesTest {

    private ControladorViajes viajesController;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioViaje viajeServiceMock;


    @Test
    public void sePuedenVerLosPedidosDelViaje(){
        // preparacion
        when(viajeServiceMock.buscarTodos()).thenReturn(null);

        // ejecucion
        ModelAndView modelAndView = viajesController.irAViajes();

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viajes"));
    }


}
