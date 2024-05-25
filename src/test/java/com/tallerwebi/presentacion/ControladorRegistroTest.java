package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniasDistintas;
import com.tallerwebi.dominio.excepcion.DatosIncompletos;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.servicios.ServicioRegistro;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorRegistroTest {

    ServicioRegistro servicioRegistro = mock(ServicioRegistro.class);
    ControladorRegistro controladorRegistro = new ControladorRegistro(servicioRegistro);

    @Test
    public void registroFallidoPorqueNoCompletoTodosLosDatos() {
        Usuario usuario = new Usuario("Jose", "afka@", "232453", "232453", "fdsf");
        givenNoExisteUsuario();
        doThrow(DatosIncompletos.class)
                .when(servicioRegistro)
                .registrar(usuario);
        ModelAndView mav = whenRegistroUsuario(usuario);
        thenElRegistroEsFallido(mav, "Faltan datos");
    }

    @Test
    public void registroFallidoPorquePassawordYRepeticionDePasswordSonDistintas() {
        Usuario usuario = new Usuario("Jose", "afka@gmail", "232453", "232453", "fdsf");
        givenNoExisteUsuario();
        doThrow(ContraseniasDistintas.class)
                .when(servicioRegistro)
                .registrar(usuario);
        ModelAndView mav = whenRegistroUsuario(usuario);
        thenElRegistroEsFallido(mav, "Las passwords no coinciden");
    }

    private void thenElRegistroEsFallido(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("registro"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }


    @Test
    public void conEmailYPasswordElRegistroEsExistoso() {
        Usuario usuario = new Usuario("Jose", "afka@gmail", "232453", "232453", "fdsf");
        givenNoExisteUsuario();
        ModelAndView mav = whenRegistroUsuario(usuario);
        thenElRegistroEsExistoso(mav);
    }

    private void givenNoExisteUsuario() {
    }

    private ModelAndView whenRegistroUsuario(Usuario usuario) {
        ModelAndView  mav = controladorRegistro.registrar(usuario);
        return mav;
    }

    private void thenElRegistroEsExistoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/index"));
    }


    @Test
    public void elRegistroFallaSiLaPasswordTieneMenosDeSeisCaracteres() {
        Usuario usuario = new Usuario("Jose", "afka@gmail", "23245", "23245", "fdsf");
        givenNoExisteUsuario();
        doThrow(PasswordLongitudIncorrecta.class)
                .when(servicioRegistro)
                .registrar(usuario);
        ModelAndView mav = whenRegistroUsuario(usuario);
        thenElRegistroEsFallido(mav, "Password con longitud incorrecta");
    }

}