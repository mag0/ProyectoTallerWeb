package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.repositorios.RepositorioVehiculo;
import com.tallerwebi.servicios.impl.ServicioEliminarVehiculoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ServicioEliminarVehiculoTest {

    RepositorioVehiculo repositorioVehiculo;
    ServicioEliminarVehiculo servicioEliminarVehiculo;

    @BeforeEach
    void setUp() {
        repositorioVehiculo = mock(RepositorioVehiculo.class);
        servicioEliminarVehiculo = new ServicioEliminarVehiculoImpl(repositorioVehiculo);
    }

    @Test
    public void seEliminaVehiculoConExito() {
        Vehiculo vehiculo = givenExisteUnVehiculoEnLaFlota();

        when(repositorioVehiculo.buscar(vehiculo.getId())).thenReturn(vehiculo);

        whenSeEliminaVehiculo(vehiculo);

        thenElVehiculoYaNoExiste(vehiculo);
    }

    private Vehiculo givenExisteUnVehiculoEnLaFlota(){
        return new Vehiculo("ASD123", "otro", "si", "Auto", 1100, 190, 95, 4, true);
    }

    private void whenSeEliminaVehiculo(Vehiculo vehiculo) {
        servicioEliminarVehiculo.eliminarVehiculo(vehiculo.getId());
    }

    private void thenElVehiculoYaNoExiste(Vehiculo vehiculo) {
        verify(repositorioVehiculo, times(1)).buscar(vehiculo.getId());
        verify(repositorioVehiculo, times(1)).eliminar(vehiculo);
    }
}




