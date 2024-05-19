package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FlotaDeVehiculos;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.ServicioEliminarVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ControladorGestionVehicularTest {

    FlotaDeVehiculos flotaDeVehiculos = new FlotaDeVehiculos();
    ServicioMostrarVehiculos servicioMostrarVehiculos = mock(ServicioMostrarVehiculos.class);
    ServicioEliminarVehiculo servicioEliminarVehiculo = mock(ServicioEliminarVehiculo.class);
    ControladorGestionVehicular controladorGestionVehicular = new ControladorGestionVehicular(servicioMostrarVehiculos, flotaDeVehiculos, servicioEliminarVehiculo);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controladorGestionVehicular = new ControladorGestionVehicular(servicioMostrarVehiculos, flotaDeVehiculos, servicioEliminarVehiculo);
    }

    @Test void cuandoNoHayVehiculosDeberiaMostrarMensajeDeErrorEnVista(){
        givenNoTengoVehiculos();

        doThrow(NoHayVehiculosEnLaFlota.class)
                .when(servicioMostrarVehiculos)
                .mostrarFlota(flotaDeVehiculos);


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
        // Establecer estado inicial: Hay vehículos en la flota
        givenTengoVehiculos();

        // Configurar el comportamiento del servicio para devolver una lista de vehículos
        List<Vehiculo> vehiculos = crearVehiculosDePrueba();
        when(servicioMostrarVehiculos.mostrarFlota(flotaDeVehiculos)).thenReturn(vehiculos);

        // Ejecutar la acción y verificar el resultado
        ModelAndView mav = whenReciboListaDeVehiculos();
        thenSeVaALaVistaDeGestionVehicularConVehiculos(mav, vehiculos);
    }

    private void givenTengoVehiculos() {
        // Agregar vehículos a la flota de vehículos
        List<Vehiculo> vehiculos = crearVehiculosDePrueba();
        flotaDeVehiculos.getVehiculos().addAll(vehiculos);
    }

    private void thenSeVaALaVistaDeGestionVehicularConVehiculos(ModelAndView mav, List<Vehiculo> vehiculos) {
        assertThat("La vista debería ser 'gestionVehicular'", mav.getViewName(), equalToIgnoringCase("gestionVehicular"));
        assertThat("La lista de vehículos debería ser la esperada", (List<Vehiculo>) mav.getModel().get("vehiculos"), containsInAnyOrder(vehiculos.toArray()));
    }

    private List<Vehiculo> crearVehiculosDePrueba() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo("ABC123", "Honda", "CBR600RR", "Moto", 15000, 10, 200, 1,true));
        vehiculos.add(new Vehiculo("DEF456", "Toyota", "Corolla", "Auto", 80000, 50, 300, 5,true));
        vehiculos.add(new Vehiculo("GHI789", "Volvo", "FH16", "Camión", 500000, 400, 500, 3,true));
        vehiculos.add(new Vehiculo("JKL012", "Yamaha", "MT-07", "Moto", 20000, 15, 180, 1,true));
        return vehiculos;
    }

    /*@Test
    void cuandoSeEliminaVehiculoDeberiaEliminarloDeLaFlotaYNoSaleEnGestionVehicular() {
        givenTengoVehiculos();
        Vehiculo vehiculo = flotaDeVehiculos.getVehiculos().get(3);
        whenEliminoVehiculoDeFlota(vehiculo.getPatente());

        thenNoExisteElVehiculoEnLaFlota(vehiculo);
    }

    private void thenNoExisteElVehiculoEnLaFlota(Vehiculo vehiculo) {
        assertFalse(flotaDeVehiculos.contieneVehiculo(vehiculo));
    }

    private void whenEliminoVehiculoDeFlota(String patente) {
        controladorGestionVehicular.eliminarVehiculo(patente);
    }*/

}
