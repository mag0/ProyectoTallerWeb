package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorInicioDeSesionTest {

    private List<Usuario> usuarios = new ArrayList<>();

    ServicioInicioDeSesion servicioInicioDeSesion = mock(ServicioInicioDeSesion.class);
    ControladorInicioDeSesion controladorInicioDeSesion = new ControladorInicioDeSesion(servicioInicioDeSesion);

    @Test
    public void inicioDeSesionFallidoPorqueNoExisteElUsuario() {

        Usuario usuario1 = givenExisteUnUsuario();
        Usuario usuario2 = new Usuario("Jose", "afka@gmail", "232453", "232453", "fdsf");
        usuarios.add(usuario1);

        doThrow(UsuarioInexistente.class)
                .when(servicioInicioDeSesion)
                .iniciarSesion(usuario2);

        ModelAndView mav = whenRegistroUsuario(usuario2);

        thenElRegistroEsFallido(mav, "No existe ese usuario");
    }

    public Usuario givenExisteUnUsuario(){
        return new Usuario("Messi", "", "232453", "", "");
    }

    @Test
    public void inicioDeSesionFallidoPorqueNoCoincidenLasPasswords() {
        // Configuración del usuario con contraseña incorrecta
        Usuario usuarioIncorrecto = new Usuario("1231234", "1231234", "232453", "232453", "fdsf");

        // Configuración del mock para que lance la excepción PasswordIncorrecta
        doThrow(PasswordIncorrecta.class)
                .when(servicioInicioDeSesion)
                .iniciarSesion(usuarioIncorrecto);

        // Ejecución del método bajo prueba
        ModelAndView mav = whenRegistroUsuario(usuarioIncorrecto);

        // Verificación de que el registro es fallido
        thenElRegistroEsFallido(mav, "Contraseña incorrecta");
    }

    private void thenElRegistroEsFallido(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("index"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }


    @Test
    public void siExisteElUsuarioYLaPasswordEsCorrectaLoRedirigeALavistaGestionVehicular() {
        Usuario usuario = givenExisteUnUsuario();
        Usuario usuario2 = new Usuario("Jose", "", "232453", "", "");
        usuarios.add(usuario);

        ModelAndView mav = whenRegistroUsuario(usuario2);

        thenElInicioDeSesionEsExistoso(mav);
    }

    private ModelAndView whenRegistroUsuario(Usuario usuario) {
        ModelAndView  mav = controladorInicioDeSesion.iniciarSesion(usuario);
        return mav;
    }

    private void thenElInicioDeSesionEsExistoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/gestionVehicular"));
    }
}