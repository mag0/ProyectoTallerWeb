package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioAgregarVehiculo;
import com.tallerwebi.servicios.impl.ServicioAgregarVehiculoImpl;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ServicioAgregarVehiculoTest {

    RepositorioVehiculo repositorioMock = mock(RepositorioVehiculo.class);
    ServicioAgregarVehiculo servicioAgregarVehiculo = new ServicioAgregarVehiculoImpl(repositorioMock);

    FlotaDeVehiculos flotaDeVehiculos = new FlotaDeVehiculos();

    @Test
    public void siSeRecibenTodosLosDatosDelVehiculoLaAltaEsExitosa() {
        Vehiculo vehiculo = givenSeRecibieronLosDatosDelVehiculo();
        whenAltaDeVehiculo(vehiculo);
        thenAltaDeVehiculoExistoso(vehiculo);
    }

    private Vehiculo givenSeRecibieronLosDatosDelVehiculo() {
        return new Vehiculo("ASD123","otro", "si", "Auto", 1100, 190, 95, 4,true);
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

        assertThrows(VehiculoExistente.class,()-> whenAltaDeVehiculo(vehiculo));
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
        return new Vehiculo("ASD123","otro", "other", "", 1231, 190, 95, 3,true);
    }
}
