package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioFiltrarVehiculoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ServicioFiltrarVehiculoTest {

    private ServicioFiltrarVehiculo servicioFiltrarVehiculo;
    private RepositorioVehiculo repositorioVehiculo;

    @BeforeEach
    void setUp() {
        repositorioVehiculo = mock(RepositorioVehiculo.class);
        servicioFiltrarVehiculo = new ServicioFiltrarVehiculoImpl(repositorioVehiculo);
    }

    @Test
    public void seDevuelveUnaListaDeVehiculosQueContienenElDatoRecibido() {
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos();
        String datoRecibido = "toyota";

        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);

        List<Vehiculo> vehiculosFiltrados = whenSeRecibeUnaListaDeVehiculoFiltrados(datoRecibido);

        thenSeEnviaUnaListaDeVehiculosFiltrados(vehiculosFiltrados);
    }

    private List<Vehiculo> givenTengoUnaListaDeVehiculos() {
        Vehiculo vehiculo1 = new Vehiculo("ABC123", "Honda", "CBR600RR","Moto", 15000, 10, 200, 1, true);
        Vehiculo vehiculo2 = new Vehiculo("DEF456", "Toyota", "Corolla","Auto", 80000, 50, 300, 5, true);
        Vehiculo vehiculo3 = new Vehiculo("POA123", "Toyota", "Corolla","Auto", 80000, 50, 300, 5, true);

        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);
        return vehiculos;
    }

    private List<Vehiculo> whenSeRecibeUnaListaDeVehiculoFiltrados(String datoRecibido) {
        return servicioFiltrarVehiculo.obtenerVehiculosFiltrados(datoRecibido);
    }

    private void thenSeEnviaUnaListaDeVehiculosFiltrados(List<Vehiculo> vehiculosFiltrados) {
        assertThat(vehiculosFiltrados.size(), equalTo(2));
    }

    @Test
    public void siNoExistenVehiculosConElDatoEnviadoSeDevuelveMensaje() {
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos();
        String datoRecibido = "fiat";

        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);

        assertThrows(NoHayVehiculosEnLaFlota.class,
                () -> servicioFiltrarVehiculo.obtenerVehiculosFiltrados(datoRecibido));
    }
}
