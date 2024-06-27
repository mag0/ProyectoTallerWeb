package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.excepcion.NoExisteFiltrado;
import com.tallerwebi.dominio.excepcion.NoHayVehiculosEnLaFlota;
import com.tallerwebi.repositorios.RepositorioUsuario;
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
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    void setUp() {
        repositorioVehiculo = mock(RepositorioVehiculo.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioFiltrarVehiculo = new ServicioFiltrarVehiculoImpl(repositorioVehiculo, repositorioUsuario);
    }

    @Test
    public void seDevuelveUnaListaDeVehiculosQueContienenElDatoRecibido() {
        String tipo = "auto";
        String marca = "";
        String modelo = "";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setActivo(true);
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos(usuario);

        when(repositorioUsuario.buscarUsuarioActivo()).thenReturn(usuario);
        when(repositorioVehiculo.buscarTodosPorUsuario(usuario.getId())).thenReturn(vehiculos);

        List<Vehiculo> vehiculosFiltrados = whenSeRecibeUnaListaDeVehiculoFiltrados(tipo,marca,modelo);

        thenSeEnviaUnaListaDeVehiculosFiltrados(vehiculosFiltrados);
    }

    private List<Vehiculo> givenTengoUnaListaDeVehiculos(Usuario usuario) {
        Vehiculo vehiculo1 = new Vehiculo("ABC123", "Honda", "CBR600RR","Moto", 15000, 10, 200, 1, true);
        vehiculo1.setUsuarioID(usuario);
        Vehiculo vehiculo2 = new Vehiculo("DEF456", "Toyota", "Corolla","Auto", 80000, 50, 300, 5, true);
        vehiculo2.setUsuarioID(usuario);
        Vehiculo vehiculo3 = new Vehiculo("POA123", "Toyota", "Corolla","Auto", 80000, 50, 300, 5, true);
        vehiculo3.setUsuarioID(usuario);

        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);
        return vehiculos;
    }

    private List<Vehiculo> whenSeRecibeUnaListaDeVehiculoFiltrados(String tipo, String marca, String modelo) {
        return servicioFiltrarVehiculo.obtenerVehiculosFiltrados(tipo, marca, modelo);
    }

    private void thenSeEnviaUnaListaDeVehiculosFiltrados(List<Vehiculo> vehiculosFiltrados) {
        assertThat(vehiculosFiltrados.size(), equalTo(2));
    }

    @Test
    public void siNoExistenVehiculosConElDatoEnviadoSeDevuelveMensaje() {
        String tipo = "auto";
        String marca = "messi";
        String modelo = "";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setActivo(true);
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos(usuario);

        when(repositorioUsuario.buscarUsuarioActivo()).thenReturn(usuario);
        when(repositorioVehiculo.buscarTodosPorUsuario(usuario.getId())).thenReturn(vehiculos);

        assertThrows(NoHayVehiculosEnLaFlota.class,
                () -> servicioFiltrarVehiculo.obtenerVehiculosFiltrados(tipo,marca,modelo));
    }

    @Test
    public void siNoExistenFiltrosSeDevuelveMensaje() {
        String tipo = "";
        String marca = "";
        String modelo = "";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setActivo(true);
        List<Vehiculo> vehiculos = givenTengoUnaListaDeVehiculos(usuario);

        when(repositorioUsuario.buscarUsuarioActivo()).thenReturn(usuario);
        when(repositorioVehiculo.buscarTodosPorUsuario(usuario.getId())).thenReturn(vehiculos);

        assertThrows(NoExisteFiltrado.class,
                () -> servicioFiltrarVehiculo.obtenerVehiculosFiltrados(tipo,marca,modelo));
    }
}
