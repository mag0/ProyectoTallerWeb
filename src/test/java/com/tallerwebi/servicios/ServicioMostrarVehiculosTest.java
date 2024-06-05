package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioMostrarVehiculosImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioMostrarVehiculosTest {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioVehiculo repositorioVehiculo;
    private ServicioMostrarVehiculos servicioMostrarVehiculos;

    @BeforeEach
    void setUp() {
        repositorioVehiculo = mock(RepositorioVehiculo.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioMostrarVehiculos = new ServicioMostrarVehiculosImpl(repositorioVehiculo, repositorioUsuario);
    }

    @Test
    public void alHaberVehiculosEnLaFlotaElServicioDevuelveLosVehiculos() {
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos();

        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);

        List<Vehiculo> vehiculosEnLaFlota = whenPidoLaListaDeVehiculos();

        assertThat(vehiculosEnLaFlota.size(), is(2));
    }

    private List<Vehiculo> givenTengoUnaListaDeVehiculos() {
        Vehiculo vehiculo1 = new Vehiculo("ABC123", "Honda", "CBR600RR","Moto", 15000, 10, 200, 1, true);
        Vehiculo vehiculo2 = new Vehiculo("DEF456", "Toyota", "Corolla","Auto", 80000, 50, 300, 5, true);
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        return vehiculos;
    }

    private List<Vehiculo> whenPidoLaListaDeVehiculos() {
        return servicioMostrarVehiculos.obtenerVehiculosDisponibles();
    }

    @Test
    public void alNoHaberVehiculosEnLaFlotaElServicioDevuelveUnaExcepcionDeQueNoHayVehiculos() {
        givenTengoUnaListaDeVehiculosVacia();

        when(repositorioVehiculo.buscarTodos()).thenReturn(new ArrayList<>());

        assertThrows(NoHayVehiculosEnLaFlota.class,
                () -> servicioMostrarVehiculos.obtenerVehiculosDisponibles());
    }

    private void givenTengoUnaListaDeVehiculosVacia() {
    }
}

