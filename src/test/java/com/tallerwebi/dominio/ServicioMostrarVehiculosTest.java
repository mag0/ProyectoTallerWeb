package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.ServicioMostrarVehiculos;
import com.tallerwebi.servicios.impl.ServicioMostrarVehiculosImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioMostrarVehiculosTest {

    private RepositorioVehiculo repositorioVehiculo;
    private ServicioMostrarVehiculos servicioMostrarVehiculos;
    private FlotaDeVehiculos flotaDeVehiculos;

    @BeforeEach
    public void setUp() {
        repositorioVehiculo = Mockito.mock(RepositorioVehiculo.class);
        flotaDeVehiculos = new FlotaDeVehiculos();
        servicioMostrarVehiculos = new ServicioMostrarVehiculosImpl(repositorioVehiculo, flotaDeVehiculos);
    }

    @Test
    public void alHaberVehiculosEnLaFlotaElServicioDevuelveLosVehiculos() {
        givenVehiculosEnLaBaseDeDatos();
        List<Vehiculo> vehiculos = whenVerificoSiHayVehiculosEnLaFlota();
        assertThat(vehiculos, is(notNullValue()));
        assertThat(vehiculos.size(), is(4));
    }

    private void givenVehiculosEnLaBaseDeDatos() {
        List<Vehiculo> vehiculos = List.of(
                new Vehiculo("ABC123", "Honda", "CBR600RR", "Moto", 15000, 10, 200, 1),
                new Vehiculo("DEF456", "Toyota", "Corolla", "Auto", 80000, 50, 300, 5),
                new Vehiculo("GHI789", "Volvo", "FH16", "Camión", 500000, 400, 500, 3),
                new Vehiculo("JKL012", "Yamaha", "MT-07", "Moto", 20000, 15, 180, 1)
        );
        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);
    }

    private List<Vehiculo> whenVerificoSiHayVehiculosEnLaFlota() {
        return servicioMostrarVehiculos.mostrarFlota(flotaDeVehiculos);
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

