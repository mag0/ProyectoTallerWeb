package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioFiltrarVehiculo;
import com.tallerwebi.servicios.ServicioFiltrarVehiculoTest;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioEliminarVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorGestionVehicularTest {

    ServicioMostrarVehiculos servicioMostrarVehiculos = mock(ServicioMostrarVehiculos.class);
    ServicioEliminarVehiculo servicioEliminarVehiculo = mock(ServicioEliminarVehiculo.class);
    ServicioFiltrarVehiculo servicioFiltrarVehiculo = mock(ServicioFiltrarVehiculo.class);
    ControladorGestionVehicular controladorGestionVehicular;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controladorGestionVehicular = new ControladorGestionVehicular(servicioMostrarVehiculos, servicioEliminarVehiculo, servicioFiltrarVehiculo);
    }

    @Test void cuandoNoHayVehiculosDeberiaMostrarMensajeDeErrorEnVista(){
        givenNoTengoVehiculos();

        doThrow(NoHayVehiculosEnLaFlota.class)
                .when(servicioMostrarVehiculos)
                .obtenerVehiculosDisponibles();


        ModelAndView mav = whenReciboListaDeVehiculos();

        thenSeVaALaVistaDeGestionVehicular(mav, "No hay vehiculos en la flota");
    }

    private void givenNoTengoVehiculos() {
    }

    private ModelAndView whenReciboListaDeVehiculos() {
        return controladorGestionVehicular.irAGestionVehicular();
    }

    private void thenSeVaALaVistaDeGestionVehicular(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("gestionVehicular"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }

    @Test
    void cuandoHayVehiculosDeberiaMostrarListaDeVehiculos() {
        List<Vehiculo> vehiculos = givenVehiculosDePrueba();

        when(servicioMostrarVehiculos.obtenerVehiculosDisponibles()).thenReturn(vehiculos);

        ModelAndView mav = whenReciboListaDeVehiculos();
        thenSeVaALaVistaDeGestionVehicularConVehiculos(mav, vehiculos);
    }

    private void thenSeVaALaVistaDeGestionVehicularConVehiculos(ModelAndView mav, List<Vehiculo> vehiculos) {
        assertThat("La vista debería ser 'gestionVehicular'", mav.getViewName(), equalToIgnoringCase("gestionVehicular"));
        assertThat("La lista de vehículos debería ser la esperada", (List<Vehiculo>) mav.getModel().get("vehiculos"), containsInAnyOrder(vehiculos.toArray()));
    }

    private List<Vehiculo> givenVehiculosDePrueba() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo("ABC123", "Honda", "CBR600RR", "Moto", 15000, 10, 200, 1,true));
        vehiculos.add(new Vehiculo("DEF456", "Toyota", "Corolla", "Auto", 80000, 50, 300, 5,true));
        vehiculos.add(new Vehiculo("GHI789", "Volvo", "FH16", "Camión", 500000, 400, 500, 3,true));
        vehiculos.add(new Vehiculo("JKL012", "Honda", "MT-07", "Moto", 20000, 15, 180, 1,true));
        return vehiculos;
    }
}
