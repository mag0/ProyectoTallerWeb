package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioMostrarVehiculosTest {

    ServicioMostrarVehiculos servicioMostrarVehiculos = new ServicioMostrarVehiculosImpl();

    @Test
    public void alHaberVehiculosEnLaFlotaElServicioDevuelveLosVehiculos() {
        givenNoHayVehiculosEnLaFlota();
        List<Vehiculo> vehiculos= whenVerificoSiHayVehiculosEnLaFlota();
        assertThat(vehiculos,is(notNullValue()));
    }
    private void givenNoHayVehiculosEnLaFlota() {
    }
    private List<Vehiculo> whenVerificoSiHayVehiculosEnLaFlota() {
        return servicioMostrarVehiculos.mostrarFlota(new FlotaDeVehiculos());
    }
/*
    @Test
    public void alNoHaberVehiculosEnLaFlotaElServicioDevuelveUnaExcepcionDeQueNoHayVehiculos() {
        givenNoHayVehiculosEnLaFlota();
        assertThrows(NoHayVehiculosEnLaFlota.class,()-> whenVerificoSiHayVehiculosEnLaFlota());
    }



    */
}
