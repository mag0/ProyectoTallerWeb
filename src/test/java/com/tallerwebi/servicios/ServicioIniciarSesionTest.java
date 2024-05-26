package com.tallerwebi.servicios;

import com.tallerwebi.servicios.impl.ServicioIniciarSesionImpl;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.servicios.impl.ServicioRegistroImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

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
    private ServicioInicioDeSesion servicioInicioDeSesion = new ServicioIniciarSesionImpl(repositorioUsuario);

    /*@Test
    public void siElUsuarioExisteYLaPasswordEsCorrectaIniciaSesion() {
        Usuario usuario = givenExisteUnUsuarioRegistrado();

        whenUsuarioIniciaSesion(usuario);

        thenSeIniciaSesion(usuario);
    }*/

    private Usuario givenExisteUnUsuarioRegistrado() {
        Usuario usuario = new Usuario("messi", "correctPassword", "email@gmail.com", "admin", "fdsf");
        usuario.setActivo(false);
        return usuario;
    }

    private void whenUsuarioIniciaSesion(Usuario usuario) {
        servicioInicioDeSesion.iniciarSesion(usuario);
    }

    private void thenSeIniciaSesion(Usuario usuario) {
        assertTrue(usuario.getActivo());
    }

    @Test
    public void siElUsuarioNoExisteSeLanzaEx() {
        givenExisteUnUsuarioRegistrado();
        assertThrows(UsuarioInexistente.class,
                ()-> whenRegistroUsuario(new Usuario("Messi", "afka@gmail", "232453", "232453", "fdsf")));
    }

    private void whenRegistroUsuario(Usuario usuario) {
        servicioInicioDeSesion.iniciarSesion(usuario);
    }

    private void thenElRegitroEsExistoso() {}

    /*@Test
    public void siLaPasswordNoEsCorrectaSeLanzaEx() {
        givenExisteUnUsuarioRegistrado();
        Usuario usuario = new Usuario("Jose1234", "Jose1234", "234531", "admin", "fdsf");
        assertThrows(PasswordIncorrecta.class,
                ()-> whenRegistroUsuario(usuario));
    }*/

}
