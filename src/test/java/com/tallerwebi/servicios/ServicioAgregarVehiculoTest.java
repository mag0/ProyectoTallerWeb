package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioAgregarVehiculoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioAgregarVehiculoTest {

    private ServicioAgregarVehiculo servicioAgregarVehiculo;
    private RepositorioVehiculo repositorioVehiculo;

    @BeforeEach
    void setUp() {
        repositorioVehiculo = mock(RepositorioVehiculo.class);
        servicioAgregarVehiculo = new ServicioAgregarVehiculoImpl(repositorioVehiculo);
    }

    @Test
    public void siNoSeRecibenTodosLosDatosDelVehiculoNoSeDaDeAlta() {
        givenNoExisteVehiculo();
        
        Vehiculo vehiculo = whenSeRecibeUnVehiculoConLosDatosIncompletos();
        
        assertThrows(DatosIncompletos.class, () -> servicioAgregarVehiculo.agregarVehiculo(vehiculo));
    }

    private void givenNoExisteVehiculo() {
    }

    private Vehiculo whenSeRecibeUnVehiculoConLosDatosIncompletos() {
        return new Vehiculo("ASD1123", "", "si", "Auto", 1100, 190, 95, 4, true);
    }

    @Test
    public void elVehiculoNoSeAgregaYaQueExisteEnLaFlota() {
        ArrayList<Vehiculo> vehiculos = givenTengoUnVehiculoEnLaListaDeVehiculos();

        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);

        thenElVehiculoNoSeAgrega(vehiculoParaDarDeAlta());
    }

    private Vehiculo vehiculoParaDarDeAlta() {
        return new Vehiculo("ASD1123", "otro", "si", "Auto", 1100, 190, 95, 4, true);
    }

    private void thenElVehiculoNoSeAgrega(Vehiculo vehiculo) {
        assertThrows(VehiculoExistente.class, () -> servicioAgregarVehiculo.agregarVehiculo(vehiculo));
    }

    private ArrayList<Vehiculo> givenTengoUnVehiculoEnLaListaDeVehiculos() {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(vehiculoParaDarDeAlta());
        return vehiculos;
    }

    @Test
    public void seRecibenTodosLosDatosDelVehiculoYElAltaEsExitosa() {
        givenNoExisteVehiculo();

        whenEnvioLosDatosDeUnVehiculo(vehiculoParaDarDeAlta());

        thenElVehiculoEsAgregadoConExisto(vehiculoParaDarDeAlta());
    }

    private void thenElVehiculoEsAgregadoConExisto(Vehiculo vehiculo) {
        verify(repositorioVehiculo, times(1)).guardar(vehiculo);
    }

    private void whenEnvioLosDatosDeUnVehiculo(Vehiculo vehiculo) {
        servicioAgregarVehiculo.agregarVehiculo(vehiculo);
    }

}

