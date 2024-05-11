package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioIniciarSesionTest {
    Usuario1 usuario1 = new Usuario1();
    Usuarios usuarios = new Usuarios();
    ServicioInicioDeSesion servicioInciciarSesion = new ServicioIniciarSesionImpl();

    @Test
    public void siElUsuarioExisteNoSeLanzaEx() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "232453", "232453", "fdsf");
        Usuario1 usuario2 = new Usuario1("Jose", "", "232453", "", "");
        usuarios.agregarUsuario(usuario);
        whenUsuarioIniciaSesion(usuario2, usuarios);
        thenSeIniciaSesion();
    }

    @Test
    public void siElUsuarioNoExisteSeLanzaEx() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "232453", "232453", "fdsf");
        Usuario1 usuario2 = new Usuario1("Messi", "", "232453", "", "");
        usuarios.agregarUsuario(usuario);
        assertThrows(UsuarioInexistente.class,
                ()-> whenRegistroUsuario(usuario2));
        thenElRegitroEsExistoso();
    }

    private void givenNoExisteUsuario() {}

    private void whenRegistroUsuario(Usuario1 usuario) {
        servicioInciciarSesion.iniciarSesion(usuario,usuarios);
    }

    private void thenElRegitroEsExistoso() {}

    @Test
    public void siLaPasswordNoEsCorrectaSeLanzaEx() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "", "234531", "234531", "fdsf");
        Usuario1 usuario2 = new Usuario1("Jose", "", "2345311", "", "");
        usuarios.agregarUsuario(usuario);
        assertThrows(PasswordIncorrecta.class,
                ()-> whenRegistroUsuario(usuario2));
    }

    @Test
    public void siLaPasswordEsCorrectaNoSeLanzaEx() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "", "234531", "234531", "fdsf");
        Usuario1 usuario2 = new Usuario1("Jose", "", "234531", "", "");
        usuarios.agregarUsuario(usuario);
        whenUsuarioIniciaSesion(usuario2, usuarios);
        thenSeIniciaSesion();
    }

    private void whenUsuarioIniciaSesion(Usuario1 usuario, Usuarios usuarios) {
        servicioInciciarSesion.iniciarSesion(usuario,usuarios);
    }

    private void thenSeIniciaSesion() {
    }
}
