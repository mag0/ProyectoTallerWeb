package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioMostrarVehiculosImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioMostrarVehiculosTest {

    private RepositorioVehiculo repositorioVehiculo = Mockito.mock(RepositorioVehiculo.class);
    private ServicioMostrarVehiculos servicioMostrarVehiculos = new ServicioMostrarVehiculosImpl(repositorioVehiculo);

    @Test
    public void alHaberVehiculosEnLaFlotaElServicioDevuelveLosVehiculos() {
        givenVehiculosEnLaBaseDeDatos();
        List<Vehiculo> vehiculos = whenVerificoSiHayVehiculosEnLaFlota();
        assertThat(vehiculos, is(notNullValue()));
        assertThat(vehiculos.size(), is(2));
    }

    private void givenVehiculosEnLaBaseDeDatos() {
        Vehiculo vehiculo = new Vehiculo("ABC123", "Honda", "CBR600RR",
                "Moto", 15000, 10, 200, 1, true);
        Vehiculo vehiculo2 = new Vehiculo("DEF456", "Toyota", "Corolla",
                "Auto", 80000, 50, 300, 5, true);
        List<Vehiculo> vehiculos = Arrays.asList(vehiculo, vehiculo2);

        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);
    }

    private List<Vehiculo> whenVerificoSiHayVehiculosEnLaFlota() {
        return servicioMostrarVehiculos.obtenerVehiculosDisponibles();
    }

    @Test
    public void alNoHaberVehiculosEnLaFlotaElServicioDevuelveUnaExcepcionDeQueNoHayVehiculos() {
        givenNoHayVehiculosEnLaBaseDeDatos();
        assertThrows(NoHayVehiculosEnLaFlota.class, this::whenVerificoSiHayVehiculosEnLaFlota);
    }

    private void givenNoHayVehiculosEnLaBaseDeDatos() {
        when(repositorioVehiculo.buscarTodos()).thenReturn(new ArrayList<>());
    }
}

