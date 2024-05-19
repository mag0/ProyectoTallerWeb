package com.tallerwebi.dominio;

import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioEliminarVehiculoImpl;
import com.tallerwebi.servicios.ServicioEliminarVehiculo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ServicioEliminarVehiculoTest {

    RepositorioVehiculo repositorioMock = mock(RepositorioVehiculo.class);
    ServicioEliminarVehiculo servicioEliminarVehiculo = new ServicioEliminarVehiculoImpl(repositorioMock);
    FlotaDeVehiculos flotaDeVehiculos = new FlotaDeVehiculos();

    @Test
    public void seEliminaVehiculoConExisto() {
        Vehiculo vehiculo = givenSeRecibeVehiculo();
        whenSeEliminaVehiculo(vehiculo);
        thenNoExisteElVehiculo(vehiculo);
    }

    private Vehiculo givenSeRecibeVehiculo() {
        return new Vehiculo("ASD123","otro", "si", "Auto", 1100, 190, 95, 4,true);
    }

    private void whenSeEliminaVehiculo(Vehiculo vehiculo) {
        servicioEliminarVehiculo.eliminarVehiculo(vehiculo,flotaDeVehiculos);
    }

    private void thenNoExisteElVehiculo(Vehiculo vehiculo) {
        assertFalse(flotaDeVehiculos.contieneVehiculo(vehiculo));
    }
}


