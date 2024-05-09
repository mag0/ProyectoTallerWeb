package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorGestionVehicularTest {

    FlotaDeVehiculos flotaDeVehiculos = new FlotaDeVehiculos();
    ServicioMostrarVehiculos servicioMostrarVehiculos = mock(ServicioMostrarVehiculos.class);
    ControladorGestionVehicular controladorGestionVehicular = new ControladorGestionVehicular(servicioMostrarVehiculos);

    @Test void noSeLogranMostrarLosVehiculosDebidoAQueNoHay(){
        givenNoTengoVehiculos();

        doThrow(NoHayVehiculosEnLaFlota.class)
                .when(servicioMostrarVehiculos)
                .mostrarFlota();


        ModelAndView mav = whenReciboListaDeVehiculos();

        thenSeVaALaVistaDeGestionVehicular(mav, "No hay vehiculos en la flota");
    }

    private void givenNoTengoVehiculos() {
    }

    private ModelAndView whenReciboListaDeVehiculos() {
        ModelAndView mav = controladorGestionVehicular.irAGestionVehicular();
        return mav;
    }

    private void thenSeVaALaVistaDeGestionVehicular(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("gestionVehicular"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }

}
