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
        whenAltaDeVehiculo(vehiculo,flotaDeVehiculos);
        thenAltaDeVehiculoExistoso(vehiculo);
    }

    private Vehiculo givenSeRecibieronLosDatosDelVehiculo() {
        return new Vehiculo("otro", "si", "Auto", 1100, 190, 95, 4);
    }

    private void whenAltaDeVehiculo(Vehiculo vehiculo,FlotaDeVehiculos flotaDeVehiculos) {
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
                ()-> whenAltaDeVehiculo(vehiculo,flotaDeVehiculos));
    }

    private void givenTengoUnVehiculoEnLaFlota() {
        whenAltaDeVehiculo(givenSeRecibieronLosDatosDelVehiculo(),flotaDeVehiculos);
    }

    @Test
    public void siNoSeRecibenTodosLosDatosDelVehiculoNoSeDaDeAlta() {
        Vehiculo vehiculo = givenNoSeRecibenTodosLosDatosDelVehiculo();
        assertThrows(DatosIncompletos.class,
                ()-> whenAltaDeVehiculo(vehiculo,flotaDeVehiculos));
    }

    private Vehiculo givenNoSeRecibenTodosLosDatosDelVehiculo() {
        return new Vehiculo("otro", "other", "Auto", null, 190, 95, 4);
    }
}
