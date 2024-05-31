package com.tallerwebi.servicios;

import com.tallerwebi.servicios.impl.ServicioIniciarSesionImpl;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.repositorios.RepositorioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ServicioIniciarSesionTest {
    private RepositorioUsuario repositorioUsuario;
    private ServicioInicioDeSesion servicioInicioDeSesion ;

    @BeforeEach
    void setUp() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioInicioDeSesion = new ServicioIniciarSesionImpl(repositorioUsuario);
    }

    @Test
    public void siElUsuarioExisteYLaPasswordEsCorrectaIniciaSesion() {
        Usuario usuario = givenExisteUnUsuarioRegistrado();

        when(repositorioUsuario.buscar(usuario.getNombreUsuario())).thenReturn(usuario);

        whenUsuarioIniciaSesion(usuario);

        thenElUsuarioInicioSesionConExisto(usuario);
    }

    private Usuario givenExisteUnUsuarioRegistrado() {
        Usuario usuario = new Usuario("123456", "123456", "mag@gmail", "martin", "admin");
        usuario.setActivo(false);
        return usuario;
    }

    private void whenUsuarioIniciaSesion(Usuario usuario) {
        servicioInicioDeSesion.iniciarSesion(usuario);
    }

    private void thenElUsuarioInicioSesionConExisto(Usuario usuario) {
        assertTrue(usuario.getActivo());
        verify(repositorioUsuario, times(3)).buscar(usuario.getNombreUsuario());
        verify(repositorioUsuario, times(1)).modificar(usuario);
    }

    @Test
    public void siElUsuarioNoExisteSeLanzaEx() {
        Usuario usuario = givenExisteUnUsuarioRegistrado();

        when(repositorioUsuario.buscar(usuario.getNombreUsuario())).thenReturn(usuario);

        assertThrows(UsuarioInexistente.class,
                ()-> whenRegistroUsuario(usuarioInexistenteALoguear()));
    }

    private Usuario usuarioInexistenteALoguear(){
        return new Usuario("123456", "123456", "mag@gmail", "jose", "admin");
    }

    private void whenRegistroUsuario(Usuario usuario) {
        servicioInicioDeSesion.iniciarSesion(usuario);
    }

    @Test
    public void siLaPasswordNoEsCorrectaSeLanzaEx() {
        Usuario usuario = givenExisteUnUsuarioRegistrado();

        when(repositorioUsuario.buscar(usuario.getNombreUsuario())).thenReturn(usuario);

        assertThrows(PasswordIncorrecta.class,
                ()-> whenRegistroUsuario(usuarioConPasswordErroneaALoguear()));
    }

    private Usuario usuarioConPasswordErroneaALoguear(){
        return new Usuario("123456", "1234564", "mag@gmail", "martin", "admin");
    }

}
