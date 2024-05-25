package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.servicios.impl.ServicioRegistroImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioRegistroTest {

    private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    private ServicioRegistro servicioRegistro = new ServicioRegistroImpl(repositorioUsuario);

    @Test
    public void siElUsuarioCargaTodosLosDatosElRegistroEsExistoso() {
        Usuario usuario = givenRecibeUnUsuario();

        whenRegistroUsuario(usuario);

        // Verificación: Verificar que el método guardar del repositorio fue llamado con el usuario correcto
        thenElRegitroEsExistoso(usuario);
    }

    private Usuario givenRecibeUnUsuario() {
        return new Usuario("123456", "123456", "232453", "232453", "fdsf");
    }

    private void whenRegistroUsuario(Usuario usuario) {
        servicioRegistro.registrar(usuario);
    }

    private void thenElRegitroEsExistoso(Usuario usuario) {
        verify(repositorioUsuario, times(1)).guardar(usuario);
    }

    @Test
    public void siElUsuarioNoCargaTodosLosDatosElRegistroEsFallidoYRecibeExcepcion() {
        Usuario usuario = new Usuario("Jose", "", "234531", "234531", "fdsf");
        assertThrows(DatosIncompletos.class, () -> whenRegistroUsuario(usuario));
    }

    @Test
    public void siElUsuarioCargaUnaContraseniaMenorASeisDigitosRecibeExcepcion() {
        Usuario usuario = new Usuario("1234","1234","flcdvnsv","fdkls","fasf");

        assertThrows(PasswordLongitudIncorrecta.class, () -> servicioRegistro.registrar(usuario));
    }

    @Test
    public void siLasContraseniasSonDistintasLanzaExcepcion() {
        Usuario usuario = new Usuario("Jose", "afka@gmail", "2345", "23453", "fdsf");
        assertThrows(ContraseniasDistintas.class,
                ()-> whenRegistroUsuario(usuario));
    }
}