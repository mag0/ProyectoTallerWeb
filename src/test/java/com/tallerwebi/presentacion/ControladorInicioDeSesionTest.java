package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario1;
import com.tallerwebi.dominio.Usuarios;
import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.PasswordIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorInicioDeSesionTest {

    private Usuarios usuarios = new Usuarios();

    ServicioInicioDeSesion servicioInicioDeSesion = mock(ServicioInicioDeSesion.class);
    ControladorInicioDeSesion controladorInicioDeSesion = new ControladorInicioDeSesion(servicioInicioDeSesion, usuarios);

    @Test
    public void inicioDeSesionFallidoPorqueNoExisteElUsuario() {

        Usuario1 usuario2 = new Usuario1("Messi", "", "232453", "", "");
        Usuario1 usuario1 = new Usuario1("Jose", "afka@gmail", "232453", "232453", "fdsf");
        usuarios.agregarUsuario(usuario1);
        givenNoExisteUsuario();
        doThrow(UsuarioInexistente.class)
                .when(servicioInicioDeSesion)
                .iniciarSesion(usuario2,usuarios);
        ModelAndView mav = whenRegistroUsuario(usuario2);
        thenElRegistroEsFallido(mav, "No existe ese usuario");
    }

    @Test
    public void inicioDeSesionFallidoPorqueNoCoincidenLasPasswords() {
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "2324531", "232453", "fdsf");
        Usuario1 usuario2 = new Usuario1("Jose", "", "232453", "", "");
        usuarios.agregarUsuario(usuario);
        givenNoExisteUsuario();
        doThrow(PasswordIncorrecta.class)
                .when(servicioInicioDeSesion)
                .iniciarSesion(usuario2,usuarios);
        ModelAndView mav = whenRegistroUsuario(usuario);
        thenElRegistroEsFallido(mav, "Contraseña incorrecta");
    }

    private void thenElRegistroEsFallido(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("index"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }


    @Test
    public void siExisteElUsuarioYLaPasswordEsCorrectaLoRedirigeALavistaGestionVehicular() {
        givenNoExisteUsuario();
        Usuario1 usuario = new Usuario1("Jose", "afka@gmail", "232453", "232453", "fdsf");
        Usuario1 usuario2 = new Usuario1("Jose", "", "232453", "", "");
        usuarios.agregarUsuario(usuario);
        ModelAndView mav = whenRegistroUsuario(usuario2);
        thenElInicioDeSesionEsExistoso(mav);
    }

    private void givenNoExisteUsuario() {
    }

    private ModelAndView whenRegistroUsuario(Usuario1 usuario) {
        ModelAndView  mav = controladorInicioDeSesion.iniciarSesion(usuario);
        return mav;
    }

    private void thenElInicioDeSesionEsExistoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/gestionVehicular"));
    }

}