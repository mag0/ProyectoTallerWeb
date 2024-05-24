package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.VehiculoExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioAgregarVehiculoTest {

    private ServicioAgregarVehiculo servicioAgregarVehiculo;
    private ServicioMostrarVehiculos servicioMostrarVehiculos;

    @BeforeEach
    void setUp() {
        servicioAgregarVehiculo = mock(ServicioAgregarVehiculo.class);
        servicioMostrarVehiculos = mock(ServicioMostrarVehiculos.class);
    }

    @Test
    public void siSeRecibenTodosLosDatosDelVehiculoLaAltaEsExitosa() {
        // Given
        Vehiculo vehiculo = new Vehiculo("ASD1123", "otro", "si", "Auto", 1100, 190, 95, 4, true);

        // When
        when(servicioMostrarVehiculos.obtenerVehiculosDisponibles()).thenReturn(new ArrayList<>());
        // Utilizamos doNothing() para métodos void
        doNothing().when(servicioAgregarVehiculo).agregarVehiculo(vehiculo);

        // Then
        assertDoesNotThrow(() -> servicioAgregarVehiculo.agregarVehiculo(vehiculo));
        verify(servicioAgregarVehiculo, times(1)).agregarVehiculo(vehiculo);
    }

    @Test
    public void siElVehiculoExisteDevuelveExVehiculoExistente() {
        // Given
        Vehiculo vehiculo = new Vehiculo("ASD1123", "otro", "si", "Auto", 1100, 190, 95, 4, true);

        // When
        when(servicioMostrarVehiculos.obtenerVehiculosDisponibles()).thenReturn(List.of(vehiculo));
        // Utilizamos doThrow() para métodos void que lanzan una excepción
        doThrow(VehiculoExistente.class).when(servicioAgregarVehiculo).agregarVehiculo(vehiculo);

        // Then
        assertThrows(VehiculoExistente.class, () -> servicioAgregarVehiculo.agregarVehiculo(vehiculo));
        verify(servicioAgregarVehiculo, times(1)).agregarVehiculo(vehiculo);
    }


    @Test
    public void siNoSeRecibenTodosLosDatosDelVehiculoNoSeDaDeAlta() {
        // Given
        Vehiculo vehiculo = new Vehiculo("ASD123", "otro", "other", "", 1231, 190, 95, 3, true);

        // When
        // Configuramos el comportamiento del método agregarVehiculo para que lance DatosIncompletos
        doThrow(DatosIncompletos.class).when(servicioAgregarVehiculo).agregarVehiculo(vehiculo);

        // Then
        assertThrows(DatosIncompletos.class, () -> servicioAgregarVehiculo.agregarVehiculo(vehiculo));
        // Verificamos que el método agregarVehiculo fue llamado con el vehículo especificado
        verify(servicioAgregarVehiculo, times(1)).agregarVehiculo(vehiculo);
    }

}

