package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.servicios.ServicioRegistro;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioRegistroTest {

    Usuarios usuarios = new Usuarios();
    ServicioRegistro servicioUsuario = new ServicioRegistroImpl(usuarios);

    @Test
    public void siElUsuarioCargaTodosLosDatosElRegistroEsExistoso() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "232453", "232453", "fdsf");
        whenRegistroUsuario(usuario);
        thenElRegitroEsExistoso();
    }

    private void givenNoExisteUsuario() {}

    private void whenRegistroUsuario(Usuario1 usuario) {
        servicioUsuario.registrar(usuario);
    }

    private void thenElRegitroEsExistoso() {}

    @Test
    public void siElUsuarioNoCargaTodosLosDatosElRegistroEsFallidoYRecibeExcepcion() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "", "234531", "234531", "fdsf");
        assertThrows(DatosIncompletos.class,
                ()-> whenRegistroUsuario(usuario));
    }

    @Test
    public void siElUsuarioCargaUnaContraseniaMenorASeisDigitosRecibeExcepcion() {
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "23453", "23453", "fdsf");
        givenNoExisteUsuario();
        assertThrows(PasswordLongitudIncorrecta.class,
                ()-> whenRegistroUsuario(usuario));
    }

    @Test
    public void siLasContraseniasSonDistintasLanzaExcepcion() {
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "2345", "23453", "fdsf");
        givenNoExisteUsuario();
        assertThrows(ContraseniasDistintas.class,
                ()-> whenRegistroUsuario(usuario));
    }
}