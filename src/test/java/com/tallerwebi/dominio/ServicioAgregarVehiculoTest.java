package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioAgregarVehiculoTest {

    ServicioAgregarVehiculo servicioAgregarVehiculo = new ServicioAgregarVehiculoImpl();

    FlotaDeVehiculos flotaDeVehiculos = new FlotaDeVehiculos();

    @Test
    public void siSeRecibenTodosLosDatosDelVehiculoLaAltaEsExitosa() {
        Vehiculo vehiculo = givenSeRecibieronLosDatosDelVehiculo();
        whenAltaDeVehiculo(vehiculo);
        thenAltaDeVehiculoExistoso(vehiculo);
    }

    private Vehiculo givenSeRecibieronLosDatosDelVehiculo() {
        return new Vehiculo("otro", "si", "Auto", 1100, 190, 95, "4");
    }

    private void whenAltaDeVehiculo(Vehiculo vehiculo) {
        servicioAgregarVehiculo.agregarVehiculo(vehiculo,flotaDeVehiculos);
    }

    private void thenAltaDeVehiculoExistoso(Vehiculo vehiculo) {
        assertThat(vehiculo, notNullValue());
    }

    @Test
    public void siElVehiculoExisteNoSeDaDeAlta() {
        givenTengoUnVehiculoEnLaFlota();
        Vehiculo vehiculo = givenSeRecibieronLosDatosDelVehiculo();

        assertThrows(VehiculoExistente.class,
                ()-> whenAltaDeVehiculo(vehiculo));
    }

    private void givenTengoUnVehiculoEnLaFlota() {
        whenAltaDeVehiculo(givenSeRecibieronLosDatosDelVehiculo());
    }

    @Test
    public void siNoSeRecibenTodosLosDatosDelVehiculoNoSeDaDeAlta() {
        Vehiculo vehiculo = givenNoSeRecibenTodosLosDatosDelVehiculo();
        assertThrows(DatosIncompletos.class,
                ()-> whenAltaDeVehiculo(vehiculo));
    }

    private Vehiculo givenNoSeRecibenTodosLosDatosDelVehiculo() {
        return new Vehiculo("otro", "other", "", 1231, 190, 95, "3");
    }
}
