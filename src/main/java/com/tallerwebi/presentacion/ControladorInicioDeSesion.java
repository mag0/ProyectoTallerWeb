package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario1;
import com.tallerwebi.dominio.Usuarios;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import com.tallerwebi.servicios.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorInicioDeSesion {

    private final Usuarios usuarios;
    ServicioInicioDeSesion servicioInicioDeSesion;

    @Autowired
    public ControladorInicioDeSesion(ServicioInicioDeSesion servicioInicioDeSesion, Usuarios usuarios) {
        this.servicioInicioDeSesion = servicioInicioDeSesion;
        this.usuarios = usuarios;
    }

    @RequestMapping("/index")
    public ModelAndView irAlIndex() {
        ModelMap model = new ModelMap();
        model.addAttribute("usuario1", new Usuario1());
        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/iniciarSesion", method = RequestMethod.POST)
    public ModelAndView iniciarSesion(@ModelAttribute("usuario1") Usuario1 usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioInicioDeSesion.iniciarSesion(usuario, usuarios);
        }catch(UsuarioInexistente ex){
            return registroFallido(model,"No existe ese usuario");
        }catch(PasswordIncorrecta ex){
            return registroFallido(model,"Contraseña incorrecta");
        }

        return registroExitoso();
    }

    private ModelAndView registroExitoso() {
        return new ModelAndView("redirect:/gestionVehicular");
    }

    private ModelAndView registroFallido(ModelMap model,String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("index", model);
    }
}
