package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.servicios.ServicioInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorInicioDeSesion {

    ServicioInicioDeSesion servicioInicioDeSesion;

    @Autowired
    public ControladorInicioDeSesion(ServicioInicioDeSesion servicioInicioDeSesion) {
        this.servicioInicioDeSesion = servicioInicioDeSesion;
    }

    @RequestMapping("/index")
    public ModelAndView irAlIndex() {
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", new Usuario());
        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/iniciarSesion", method = RequestMethod.POST)
    public ModelAndView iniciarSesion(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioInicioDeSesion.iniciarSesion(usuario);
        }catch(UsuarioInexistente ex){
            return registroFallido(model,"No existe ese usuario");
        }catch(PasswordIncorrecta ex){
            return registroFallido(model,"Contraseña incorrecta");
        }

        return registroExitoso();
    }

    @RequestMapping("/cerrarSesion")
    public ModelAndView cerrarSesion() {
        servicioInicioDeSesion.cerrarSesion();
        return new ModelAndView("redirect:/index");
    }

    private ModelAndView registroExitoso() {
        return new ModelAndView("redirect:/gestionVehicular");
    }

    private ModelAndView registroFallido(ModelMap model,String mensaje) {
        model.put("error", mensaje);
        return new ModelAndView("index", model);
    }
}
