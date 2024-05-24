package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ServicioEliminarVehiculoTest {

    private final ServicioEliminarVehiculo servicioEliminarVehiculo = Mockito.mock(ServicioEliminarVehiculo.class);
    private final ServicioMostrarVehiculos servicioMostrarVehiculos = Mockito.mock(ServicioMostrarVehiculos.class);;
    private final List<Vehiculo> vehiculos = new ArrayList<>();

    @Test
    public void seEliminaVehiculoConExito() {
        Vehiculo vehiculo = givenSeRecibeVehiculo();

        vehiculos.add(vehiculo);

        // Simula que el vehículo está en la lista de vehículos disponibles
        when(servicioMostrarVehiculos.obtenerVehiculosDisponibles()).thenReturn(vehiculos);

        whenSeEliminaVehiculo(vehiculo);

        thenNoExisteElVehiculo(vehiculo);
    }

    private Vehiculo givenSeRecibeVehiculo() {
        return new Vehiculo("ASD123", "otro", "si", "Auto", 1100, 190, 95, 4, true);
    }

    private void whenSeEliminaVehiculo(Vehiculo vehiculo) {
        // Simula la eliminación del vehículo
        doAnswer(invocation -> {
            vehiculos.remove(vehiculo);
            return null;
        }).when(servicioEliminarVehiculo).eliminarVehiculo(vehiculo.getId());

        // Ejecuta la eliminación
        servicioEliminarVehiculo.eliminarVehiculo(vehiculo.getId());
    }

    private void thenNoExisteElVehiculo(Vehiculo vehiculo) {
        List<Vehiculo> vehiculos = servicioMostrarVehiculos.obtenerVehiculosDisponibles();
        assertFalse(vehiculos.contains(vehiculo));
        // Verifica que el método eliminarVehiculo fue llamado con el id correcto
        verify(servicioEliminarVehiculo).eliminarVehiculo(vehiculo.getId());
    }
}




