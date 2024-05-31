package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.repositorios.RepositorioUsuario;
import com.tallerwebi.servicios.impl.ServicioRegistroImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioRegistroTest {

    private RepositorioUsuario repositorioUsuario ;
    private ServicioRegistro servicioRegistro ;

    @BeforeEach
    void setUp() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioRegistro = new ServicioRegistroImpl(repositorioUsuario);
    }

    @Test
    public void siElUsuarioCargaTodosLosDatosElRegistroEsExistoso() {
        givenNoExisteUnUsuario();

        whenRegistroUnUsuario(usuarioParaDarDeAlta());

        thenElRegitroEsExistoso(usuarioParaDarDeAlta());
    }

    private Usuario usuarioParaDarDeAlta(){
        return new Usuario("123456", "123456", "mag@gmail.com", "jose", "admin");
    }

    private void givenNoExisteUnUsuario() {
    }

    private void whenRegistroUnUsuario(Usuario usuario) {
        servicioRegistro.registrar(usuario);
    }

    private void thenElRegitroEsExistoso(Usuario usuario) {
        verify(repositorioUsuario, times(1)).guardar(usuario);
    }

    @Test
    public void siElUsuarioNoCargaTodosLosDatosElRegistroEsFallido() {
        givenNoExisteUnUsuario();

        assertThrows(DatosIncompletos.class, () -> whenRegistroUnUsuario(usuarioConDatosIncompletosParaDarDeAlta()));
    }

    private Usuario usuarioConDatosIncompletosParaDarDeAlta(){
        return new Usuario("123456", "123456", "mag@gmail.com", "", "admin");
    }

    @Test
    public void siElUsuarioCargaUnaContraseniaMenorASeisDigitosELRegistroEsFallido() {
        givenNoExisteUnUsuario();

        assertThrows(PasswordLongitudIncorrecta.class, () -> servicioRegistro.registrar(usuarioConContraseniaMenorASeisDigitosParaDarDeAlta()));
    }

    private Usuario usuarioConContraseniaMenorASeisDigitosParaDarDeAlta(){
        return new Usuario("12345", "12345", "mag@gmail.com", "jose", "admin");
    }

    @Test
    public void siLasContraseniasSonDistintasLanzaExcepcion() {
        givenNoExisteUnUsuario();

        assertThrows(ContraseniasDistintas.class,
                ()-> whenRegistroUnUsuario(usuarioConPasswordYPasswordRepetidaIgualesParaDarDeAlta()));
    }

    private Usuario usuarioConPasswordYPasswordRepetidaIgualesParaDarDeAlta(){
        return new Usuario("1234567", "123456", "mag@gmail.com", "jose", "admin");
    }
}