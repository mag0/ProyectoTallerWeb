package com.tallerwebi.servicios;

import com.tallerwebi.servicios.impl.ServicioIniciarSesionImpl;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.servicios.impl.ServicioRegistroImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

public class ServicioIniciarSesionTest {
    private List<Usuario> usuarios = new ArrayList<>();
    private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    private ServicioInicioDeSesion servicioInciciarSesion = new ServicioIniciarSesionImpl(repositorioUsuario);
    private ServicioRegistro servicioRegistro = new ServicioRegistroImpl(repositorioUsuario);

    /*@Test
    public void siElUsuarioExisteYLaPasswordEsCorrectaIniciaSesion() {
        Usuario usuario = givenSeRegistraUnUsuario();

        when(repositorioUsuario.buscar("messi")).thenReturn(usuario);

        whenUsuarioIniciaSesion(usuario);

        assertTrue(usuario.activo());
    }*/

    private void whenUsuarioIniciaSesion(Usuario usuario) {
            servicioInciciarSesion.iniciarSesion(usuario);
    }

    private Usuario givenSeRegistraUnUsuario() {
        return new Usuario("admin12", "admin12", "email@gmail", "admin", "fdsf");
    }

    private void thenSeIniciaSesion(Usuario usuario) {
        Usuario usuarioRegistrado = repositorioUsuario.buscar(usuario.getNombreUsuario());
        assertTrue(usuarioRegistrado.getActivo());
    }

    @Test
    public void siElUsuarioNoExisteSeLanzaEx() {
        givenSeRegistraUnUsuario();
        assertThrows(UsuarioInexistente.class,
                ()-> whenRegistroUsuario(new Usuario("Messi", "afka@gmail", "232453", "232453", "fdsf")));
    }

    private void whenRegistroUsuario(Usuario usuario) {
        servicioInciciarSesion.iniciarSesion(usuario);
    }

    private void thenElRegitroEsExistoso() {}

    /*@Test
    public void siLaPasswordNoEsCorrectaSeLanzaEx() {
        givenSeRegistraUnUsuario();
        Usuario usuario = new Usuario("Jose1234", "Jose1234", "234531", "admin", "fdsf");
        assertThrows(PasswordIncorrecta.class,
                ()-> whenRegistroUsuario(usuario));
    }*/

}
